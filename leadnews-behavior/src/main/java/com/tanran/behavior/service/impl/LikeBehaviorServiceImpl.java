package com.tanran.behavior.service.impl;

import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.tanran.behavior.service.LikeBehaviorService;
import com.tanran.common.result.RespResult;
import com.tanran.model.article.pojos.ApArticleConfig;
import com.tanran.model.behavior.dtos.LikesBehaviorDto;
import com.tanran.model.behavior.pojos.ApBehaviorEntry;
import com.tanran.model.behavior.pojos.ApLikesBehavior;
import com.tanran.model.common.enums.ErrorCodeEnum;
import com.tanran.model.mappers.app.ArticleContentConfigMapper;
import com.tanran.model.mappers.app.BehaviorEntryMapper;
import com.tanran.model.mappers.app.LikeBehaviorMapper;
import com.tanran.model.user.pojos.ApUser;
import com.tanran.utils.threadlocal.AppThreadLocalUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 用户喜欢行为数据存储
 * @since 3.0.x 2022/3/23 19:30
 */
@Service
@Slf4j
public class LikeBehaviorServiceImpl implements LikeBehaviorService {
    @Autowired
    private BehaviorEntryMapper behaviorEntryMapper;
    @Autowired
    private LikeBehaviorMapper likeBehaviorMapper;
    @Autowired
    private ArticleContentConfigMapper configMapper;

    @Override
    public RespResult saveLikeBehavior(LikesBehaviorDto dto) {
        ApUser user = AppThreadLocalUtils.getUser();
        if(ObjectUtils.isEmpty(user)&&dto.getEntryId()==null){
            return RespResult.errorResult(ErrorCodeEnum.PARAM_REQUIRE);
        }
        Long id = null;
        if(!ObjectUtils.isEmpty(user)){
            id = user.getId();
        }
        if(dto.getType() == 0){
            ApBehaviorEntry apBehaviorEntry = behaviorEntryMapper.selectByUserIdOrEquipment(dto.getEquipmentId().longValue(),null);
            if(ObjectUtils.isEmpty(apBehaviorEntry)){
                RespResult.errorResult(ErrorCodeEnum.PARAM_REQUIRE);
            }

            ApLikesBehavior apLikesBehavior = new ApLikesBehavior();
            apLikesBehavior.setBehaviorEntryId(apBehaviorEntry.getEntryId());
            apLikesBehavior.setEntryId(dto.getEntryId());
            apLikesBehavior.setCreatedTime(new Date());
            apLikesBehavior.setType(dto.getType());

            //记录行为到文章的配置表中
            ApArticleConfig articleConfig = configMapper.findConfigById(dto.getEntryId(),dto.getEquipmentId());
            ApArticleConfig apArticleConfig = new ApArticleConfig();
            apArticleConfig.setArticleId(dto.getEntryId());
            apArticleConfig.setIsLike(true);
            apArticleConfig.setUserId(dto.getEquipmentId());
            /**
             * 如果文章配置不存在就添加原始数据，如果存在就更新数据
             */
            if(Objects.isNull(articleConfig)){
                apArticleConfig.setCreatedTime(new Date(System.currentTimeMillis()));
                apArticleConfig.setUpdatedTime(new Date(System.currentTimeMillis()));
                configMapper.insertSelective(apArticleConfig);
                log.info("文章喜欢配置添加成功");
            }else{
                apArticleConfig.setId(articleConfig.getId());
                apArticleConfig.setUpdatedTime(new Date((System.currentTimeMillis())));
                configMapper.updateByPrimaryKeySelective(apArticleConfig);
                log.info("文章配置更新成功");
            }

            likeBehaviorMapper.saveLikeBehavior(apLikesBehavior);

            return RespResult.okResult(ErrorCodeEnum.SUCCESS);
        }else {
            ApLikesBehavior apLikesBehavior = likeBehaviorMapper.selectLikeBehavior(dto);
            if(Objects.isNull(apLikesBehavior)){
                return RespResult.errorResult(ErrorCodeEnum.DATA_NOT_EXIST,"操作失败");
            }else {
                /**
                 * 删除行为数据并更新文章配置表
                 */
                likeBehaviorMapper.deleteById(apLikesBehavior.getId());
                ApArticleConfig articleConfig = configMapper.findConfigById(dto.getEntryId(),dto.getEquipmentId());
                if(Objects.nonNull(articleConfig)){
                    ApArticleConfig apArticleConfig = new ApArticleConfig();
                    apArticleConfig.setArticleId(dto.getEntryId());
                    apArticleConfig.setIsLike(false);
                    apArticleConfig.setUserId(dto.getEquipmentId());
                    apArticleConfig.setUpdatedTime(new Date(System.currentTimeMillis()));
                    configMapper.updateByPrimaryKeySelective(apArticleConfig);
                    return RespResult.okResult(ErrorCodeEnum.SUCCESS);
                }
                return RespResult.errorResult(ErrorCodeEnum.DATA_NOT_EXIST);
            }
        }
    }
}
