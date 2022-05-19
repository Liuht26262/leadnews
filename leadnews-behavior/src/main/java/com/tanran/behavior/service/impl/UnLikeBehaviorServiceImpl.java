package com.tanran.behavior.service.impl;

import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.tanran.behavior.service.UnLikeBehaviorService;
import com.tanran.common.result.RespResult;
import com.tanran.model.article.pojos.ApArticleConfig;
import com.tanran.model.behavior.dtos.UnLikesBehaviorDto;
import com.tanran.model.behavior.pojos.ApBehaviorEntry;
import com.tanran.model.behavior.pojos.ApUnlikesBehavior;
import com.tanran.model.common.enums.ErrorCodeEnum;
import com.tanran.model.mappers.app.ArticleContentConfigMapper;
import com.tanran.model.mappers.app.BehaviorEntryMapper;
import com.tanran.model.mappers.app.UnLikeBehaviorMapper;
import com.tanran.model.user.pojos.ApUser;
import com.tanran.utils.threadlocal.AppThreadLocalUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 不喜欢行为实现类
 * @since 3.0.x 2022/3/23 17:38
 */
@Service
@Slf4j
public class UnLikeBehaviorServiceImpl implements UnLikeBehaviorService {
    @Autowired
    private UnLikeBehaviorMapper unLikeBehaviorMapper;
    @Autowired
    private BehaviorEntryMapper behaviorEntryMapper;
    @Autowired
    private ArticleContentConfigMapper configMapper;


    @Override
    public RespResult SaveUnLikeBehavior(UnLikesBehaviorDto dto) {
        ApUser user = AppThreadLocalUtils.getUser();
        if(ObjectUtils.isEmpty(user)&&dto.getEquipmentId()==null){
            return RespResult.errorResult(ErrorCodeEnum.PARAM_REQUIRE,"缺少参数");
        }
        Long id = null;
        if(!ObjectUtils.isEmpty(user)){
            id = user.getId();
        }
        ApBehaviorEntry apBehaviorEntry = behaviorEntryMapper.selectByUserIdOrEquipment(id, dto.getEquipmentId());
        if(apBehaviorEntry==null){
            RespResult.errorResult(ErrorCodeEnum.PARAM_REQUIRE);
        }

        if(dto.getOperation() == 0){
            /**不喜欢*/
            ApUnlikesBehavior apUnlikesBehavior = new ApUnlikesBehavior();
            apUnlikesBehavior.setArticleId(dto.getEntryId());
            apUnlikesBehavior.setEntryId(dto.getEquipmentId());
            apUnlikesBehavior.setCreatedTime(new Date(System.currentTimeMillis()));
            apUnlikesBehavior.setType(dto.getType());

            //记录行为到文章的配置表中
            ApArticleConfig articleConfig = configMapper.findConfigById(dto.getEntryId(),dto.getEquipmentId());
            ApArticleConfig apArticleConfig = new ApArticleConfig();
            apArticleConfig.setArticleId(dto.getEntryId());
            apArticleConfig.setIsUnlike(true);
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
            unLikeBehaviorMapper.SaveUnLikeBeHaviorMapper(apUnlikesBehavior);
        }else{
            unLikeBehaviorMapper.deleteUnLikeBehaviorMapper(dto.getEquipmentId(),dto.getEntryId());
            ApArticleConfig articleConfig = configMapper.findConfigById(dto.getEntryId(),dto.getEquipmentId());
            if(Objects.nonNull(articleConfig)){
                ApArticleConfig apArticleConfig = new ApArticleConfig();
                apArticleConfig.setId(articleConfig.getId());
                apArticleConfig.setArticleId(dto.getEntryId());
                apArticleConfig.setIsUnlike(false);
                apArticleConfig.setUserId(dto.getEquipmentId());
                apArticleConfig.setUpdatedTime(new Date(System.currentTimeMillis()));
                configMapper.updateByPrimaryKeySelective(apArticleConfig);
                log.info("************更新取消收藏配置成功*************");
                return RespResult.okResult(ErrorCodeEnum.SUCCESS);
            }
            return RespResult.errorResult(ErrorCodeEnum.DATA_NOT_EXIST);
        }
        return RespResult.okResult(ErrorCodeEnum.SUCCESS);
    }

    /**取消不喜欢*/
    @Override
    public RespResult deleteUsweBehavior(UnLikesBehaviorDto dto) {
        return null;
    }
}
