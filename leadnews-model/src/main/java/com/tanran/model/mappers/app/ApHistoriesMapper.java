package com.tanran.model.mappers.app;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tanran.model.article.dtos.ArticleRequestDto;
import com.tanran.model.behavior.pojos.ApHistories;

public interface ApHistoriesMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ApHistories record);

    int insertSelective(ApHistories record);

    ApHistories selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ApHistories record);

    int updateByPrimaryKey(ApHistories record);

    List<ApHistories> selectArticleList( @Param("dto") ArticleRequestDto dto);
}
