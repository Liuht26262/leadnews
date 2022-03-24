package com.tanran.user.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.tanran.behavior.service.FollowBehaviorService;
import com.tanran.behavior.service.impl.FollowbehaviorServiceImpl;
import com.tanran.common.result.RespResult;
import com.tanran.common.zookeeper.sequence.Sequences;
import com.tanran.model.article.pojos.ApAuthor;
import com.tanran.model.behavior.dtos.FollowBehaviorDto;
import com.tanran.model.common.enums.AppHttpCodeEnum;
import com.tanran.model.mappers.app.ApUserFanMapper;
import com.tanran.model.mappers.app.ApUserFollowMapper;
import com.tanran.model.mappers.app.ApUserMapper;
import com.tanran.model.mappers.app.AuthorMapper;
import com.tanran.model.user.dtos.UserRelationDto;
import com.tanran.model.user.pojos.ApUser;
import com.tanran.model.user.pojos.ApUserFan;
import com.tanran.model.user.pojos.ApUserFollow;
import com.tanran.user.service.UserRelationService;
import com.tanran.utils.threadlocal.AppThreadLocalUtils;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 用户关注实现类
 * @since 3.0.x 2022/3/22 19:42
 * 1、判断参数
 * 2、查询所关注的作者是否存在
 *
 */
@Service
public class UserRelationServiceImpl implements UserRelationService {
    @Autowired
    private AuthorMapper authorMapper;
    @Autowired
    private ApUserMapper apUserMapper;
    @Autowired
    private ApUserFollowMapper apUserFollowMapper;
    @Autowired
    private ApUserFanMapper apUserFanMapper;
   /* @Autowired
    private FollowBehaviorService followBehaviorService;*/
    @Autowired
    private Sequences sequences;

    /***
     *
     * @param userRelationDto
     * @return
     */
    @Override
    public RespResult userFollow(UserRelationDto userRelationDto) {
        if(ObjectUtils.isEmpty(userRelationDto)||userRelationDto.getOperation()==null||userRelationDto.getOperation()>1){
            return RespResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE,"Operation参数错误");
        }
        Integer authorId = userRelationDto.getAuthorId();
        Integer followId = userRelationDto.getUserId();
        if(authorId == null && followId == null){
            return RespResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE,"followId和authorId不能同时为空");
        }else if(followId == null){
            ApAuthor apAuthor = authorMapper.selectAuthorById(authorId);
            if(apAuthor != null){
                followId = apAuthor.getId();
            }
        }

        if(followId == null){
            RespResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"用户不存在");
        } else{
            ApUser user = AppThreadLocalUtils.getUser();
            if(ObjectUtils.isEmpty(user)){
                return RespResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE,"用户不存在或者未登录");
            }else{
                if (userRelationDto.getOperation() == 0) {
                    return saveUserFollow(user, authorId, userRelationDto.getArticleId());
                } else {
                    return deleteUserFollow(user, authorId);
                }
            }
        }
        return RespResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
    }

    /**用户关注*/
    public RespResult saveUserFollow(ApUser user,Integer followId,Integer article){
        //判断用户是否存在
        ApUser apUser = apUserMapper.selectUserById(user.getId());
        if(ObjectUtils.isEmpty(apUser)){
            return RespResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"用户不存在");
        }
        /*查询用户关注信息表*/
        ApUserFollow apUserFollow = apUserFollowMapper.selectUserFollowByFollowId(user.getId(), followId);
        if(ObjectUtils.isEmpty(apUserFollow)){
            ApUserFan apUserFan = apUserFanMapper.slectUserFanByFanId(followId, user.getId());
            //说明没有关注，那就添加到粉丝
            if(ObjectUtils.isEmpty(apUserFan)){
                ApUserFan userFan = new ApUserFan();
                /*主键*/
                userFan.setId(sequences.sequenceApUserFan());
                userFan.setUserId(followId);
                userFan.setFansId(user.getId());
                userFan.setFansName(user.getName());
                userFan.setCreatedTime(new Date(System.currentTimeMillis()));
                userFan.setLevel((short)0);
                userFan.setIsDisplay(true);
                userFan.setIsShieldComment(false);
                userFan.setIsShieldLetter(false);

                apUserFanMapper.insertUserFan(userFan);
            }
            ApUserFollow userFollow = new ApUserFollow();
            userFollow.setId(sequences.sequenceApUserFollow());
            userFollow.setFollowId(followId);
            userFollow.setFollowName(apUser.getName());
            userFollow.setUserId(user.getId());
            userFollow.setCreatedTime(new Date(System.currentTimeMillis()));
            userFollow.setLevel((short)0);
            userFollow.setIsNotice(true);

            int result = apUserFollowMapper.insertUserFollow(userFollow);
            //保存用户关注行为
            FollowBehaviorDto dto = new FollowBehaviorDto();
            dto.setFollowId(followId);
            dto.setArticleId(article);

            // followBehaviorService.save(dto);
            new FollowbehaviorServiceImpl().save(dto);
            return RespResult.okResult(result);

        }
        return RespResult.errorResult(AppHttpCodeEnum.DATA_EXIST,"用户已经关注");
    }

    /**取消用户关注*/
    public RespResult deleteUserFollow(ApUser user,Integer followId){
        ApUserFollow apUserFollow = apUserFollowMapper.selectUserFollowByFollowId(user.getId(),followId);
        if(apUserFollow == null){
            return RespResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE,"未关注");
        }else {
            ApUserFan apUserFan = apUserFanMapper.slectUserFanByFanId(followId, user.getId());
            if(!ObjectUtils.isEmpty(apUserFan)){
                apUserFanMapper.deleteFan(followId,user.getId());
            }
            int result = apUserFollowMapper.deleteUserFollow(user.getId(), followId);
            return RespResult.okResult(result);
        }

    }
}
