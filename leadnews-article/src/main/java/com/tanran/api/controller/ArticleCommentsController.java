package com.tanran.api.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.tanran.api.articleApi.ArticleCommentsApi;
import com.tanran.api.service.ArticleCommentsService;
import com.tanran.common.result.RespResult;
import com.tanran.model.article.dtos.CommentAddDto;
import com.tanran.model.article.dtos.CommentsReqDto;
import com.tanran.model.common.enums.ErrorCodeEnum;
import com.tanran.model.user.pojos.ApUser;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 评论业务实现类
 * @since 2022/4/27 23:43
 */
@RestController
@RequestMapping("/api/v1/comments")
public class ArticleCommentsController implements ArticleCommentsApi {

    @Autowired
    private ArticleCommentsService articleCommentsService;

    @PostMapping("/select")
    @Override
    public RespResult getComments(@RequestBody(required = false) CommentsReqDto commentsReqDto) {
        return articleCommentsService.getComments(commentsReqDto);
    }

    @PostMapping("/add")
    @Override
    public RespResult addComments(@RequestBody HashMap map) throws ParseException {
        if(Objects.isNull(map)){
            return  RespResult.errorResult(ErrorCodeEnum.PARAM_INVALID);
        }

        String commentParams = JSON.toJSONString(map.get("params"));
        CommentAddDto dto = JSON.parseObject(commentParams, CommentAddDto.class);
        String userParams = JSON.toJSONString(map.get("user"));
        ApUser user = JSON.parseObject(userParams, ApUser.class);

        return articleCommentsService.addComments(user,dto);
    }
}
