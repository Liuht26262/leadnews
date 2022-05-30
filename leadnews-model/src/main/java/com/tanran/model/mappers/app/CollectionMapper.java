package com.tanran.model.mappers.app;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tanran.model.article.pojos.ApCollection;
import com.tanran.model.behavior.dtos.CollectionBehaviorDto;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 收藏查询
 * @since 3.0.x 2022/3/20 16:34
 */
public interface CollectionMapper {
    ApCollection selectCollectionForEntryId(Integer objectId,Integer entryId,Short type);

    List<ApCollection> selectCollectionByUser(Integer userId,Short type,Integer page,Integer size);

    int deleteByPrimaryKey(Integer id);

    int insert(ApCollection record);

    int insertSelective(ApCollection record);

    ApCollection selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ApCollection record);

    int updateByPrimaryKey(ApCollection record);

    void deleteByArticleAndUser(@Param("dto") CollectionBehaviorDto dto);

    List<ApCollection> selectCollectionByUserId(Integer userId);
}
