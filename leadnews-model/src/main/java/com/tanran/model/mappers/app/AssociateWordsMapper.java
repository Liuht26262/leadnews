package com.tanran.model.mappers.app;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tanran.model.article.pojos.ApAssociateWords;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/3/31 11:26
 */
public interface AssociateWordsMapper {
    List<ApAssociateWords> selectAssociateWords(@Param("word") String word,@Param("limit") int pageSize);
}
