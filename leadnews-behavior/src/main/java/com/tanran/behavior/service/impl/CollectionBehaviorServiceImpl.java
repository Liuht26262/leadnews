package com.tanran.behavior.service.impl;

import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tanran.behavior.service.CollectionBehaviorService;
import com.tanran.common.result.RespResult;
import com.tanran.model.article.pojos.ApArticleConfig;
import com.tanran.model.article.pojos.ApCollection;
import com.tanran.model.behavior.dtos.CollectionBehaviorDto;
import com.tanran.model.common.enums.ErrorCodeEnum;
import com.tanran.model.mappers.app.ArticleContentConfigMapper;
import com.tanran.model.mappers.app.CollectionMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/29 17:04
 */
@Service
@Slf4j
public class CollectionBehaviorServiceImpl implements CollectionBehaviorService {

    @Autowired
    private CollectionMapper collectionMapper;
    @Autowired
    private ArticleContentConfigMapper configMapper;

    @Override
    public RespResult collectionBehavior(CollectionBehaviorDto dto) {
        if(Objects.isNull(dto)){
            return RespResult.errorResult(ErrorCodeEnum.PARAM_INVALID);
        }

        if(dto.getOperation() == 0){
            /**收藏*/
            ApCollection apCollection = new ApCollection();
            apCollection.setBehaviorEntryId(dto.getEntryId());
            apCollection.setCollectionTime(new Date(System.currentTimeMillis()));
            apCollection.setPublishedTime(dto.getPublishedTime());
            apCollection.setType((short) 0);
            apCollection.setBehaviorEntryId(dto.getEquipmentId());
            apCollection.setEntryId(dto.getEntryId());

            //记录行为到文章的配置表中
            ApArticleConfig articleConfig = configMapper.findConfigById(dto.getEntryId(),dto.getEquipmentId());
            ApArticleConfig apArticleConfig = new ApArticleConfig();
            apArticleConfig.setArticleId(dto.getEntryId());
            apArticleConfig.setIsCollect(true);
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
            collectionMapper.insert(apCollection);
        }else{
            collectionMapper.deleteByArticleAndUser(dto);
            ApArticleConfig articleConfig = configMapper.findConfigById(dto.getEntryId(),dto.getEquipmentId());
            if(Objects.nonNull(articleConfig)){
                ApArticleConfig apArticleConfig = new ApArticleConfig();
                apArticleConfig.setId(articleConfig.getId());
                apArticleConfig.setArticleId(dto.getEntryId());
                apArticleConfig.setIsCollect(false);
                apArticleConfig.setUserId(dto.getEquipmentId());
                apArticleConfig.setUpdatedTime(new Date(System.currentTimeMillis()));
                configMapper.updateByPrimaryKeySelective(apArticleConfig);
                log.info("************更新取消收藏配置成功*************");

                //删除文章收藏表中的数据
                collectionMapper.deleteByArticleAndUser(dto);
                return RespResult.okResult(ErrorCodeEnum.SUCCESS);
            }
            return RespResult.errorResult(ErrorCodeEnum.DATA_NOT_EXIST);
        }
        return RespResult.okResult(ErrorCodeEnum.SUCCESS);
    }
}
