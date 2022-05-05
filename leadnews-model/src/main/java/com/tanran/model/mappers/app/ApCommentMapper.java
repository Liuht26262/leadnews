package com.tanran.model.mappers.app;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tanran.model.article.dtos.CommentsReqDto;
import com.tanran.model.article.pojos.ApComment;

public interface ApCommentMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ApComment record);

    int insertSelective(ApComment record);

    ApComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ApComment record);

    int updateByPrimaryKey(ApComment record);

    List<ApComment> selectCommentByArticle(@Param("dto") CommentsReqDto dto);

    /**统计评论总数*/
    Integer commentCount(Integer articleId);

    ApComment selectLastComments(Integer articleId);

    ApComment selectCommentRepay(int intValue, Date date);
}