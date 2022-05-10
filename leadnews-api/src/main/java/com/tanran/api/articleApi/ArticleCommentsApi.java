package com.tanran.api.articleApi;

import java.text.ParseException;
import java.util.HashMap;

import com.tanran.common.result.RespResult;
import com.tanran.model.article.dtos.CommentsReqDto;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/27 23:40
 */
public interface ArticleCommentsApi {
    RespResult getComments(CommentsReqDto commentsReqDto);
    RespResult addComments(HashMap params) throws ParseException;
}