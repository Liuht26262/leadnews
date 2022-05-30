package com.tanran.model.mappers.app;

import com.tanran.model.article.pojos.ApHotWords;


public interface ApHotWordsMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ApHotWords record);

    int insertSelective(ApHotWords record);

    ApHotWords selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ApHotWords record);

    int updateByPrimaryKey(ApHotWords record);

    void deleteByWords(String title);
}