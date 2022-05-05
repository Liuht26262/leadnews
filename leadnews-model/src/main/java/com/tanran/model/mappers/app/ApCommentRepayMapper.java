package com.tanran.model.mappers.app;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tanran.model.article.dtos.CommentsReqDto;
import com.tanran.model.article.pojos.ApCommentRepay;

public interface ApCommentRepayMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ApCommentRepay record);

    int insertSelective(ApCommentRepay record);

    ApCommentRepay selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective( ApCommentRepay record);

    int updateByPrimaryKey(ApCommentRepay record);

    List<ApCommentRepay> selectByUser(@Param("dto")CommentsReqDto dto);

    Integer countRepay(Integer commentId);

    ApCommentRepay selectCommentRepay(int authorId, Date date);
}