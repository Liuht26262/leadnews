package com.tanran.model.mappers.app;

import com.tanran.model.article.pojos.ApAssociateWords;

public interface ApAssociateWordsMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(ApAssociateWords record);

    int insertSelective(ApAssociateWords record);

    ApAssociateWords selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ApAssociateWords record);

    int updateByPrimaryKey(ApAssociateWords record);

    void deleteByKeyWords(String title);
}