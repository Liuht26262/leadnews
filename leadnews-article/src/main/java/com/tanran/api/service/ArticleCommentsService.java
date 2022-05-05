package com.tanran.api.service;

import com.tanran.common.result.RespResult;
import com.tanran.model.article.dtos.CommentAddDto;
import com.tanran.model.article.dtos.CommentsReqDto;
import com.tanran.model.user.pojos.ApUser;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/27 23:43
 */
public interface ArticleCommentsService {
    RespResult getComments(CommentsReqDto commentsReqDto);

    RespResult addComments(ApUser user,CommentAddDto dto);
}
