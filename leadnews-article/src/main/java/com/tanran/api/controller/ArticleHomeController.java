package com.tanran.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanran.api.articleApi.ArticleHomeControllerApi;
import com.tanran.api.service.ArticleHomeService;
import com.tanran.common.constans.ArticleConstans;
import com.tanran.common.result.RespResult;
import com.tanran.model.article.dtos.ArticleHomeDto;
import com.tanran.model.article.dtos.ArticleRequestDto;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @Description 首页
 * @since 3.0.x 2022/3/18 10:18
 */

@RestController
@RequestMapping("/api/v1/article")
public class ArticleHomeController implements ArticleHomeControllerApi {

    @Autowired
    private ArticleHomeService articleHomeService;

    @Override
    @PostMapping("/load")
    public RespResult load(ArticleHomeDto dto) {
        return articleHomeService.load(dto, ArticleConstans.LOADTYPE_LOAD_MORE);
    }

    @Override
    @PostMapping("/loadmore")
    public RespResult loadMore(ArticleHomeDto dto) {
        return articleHomeService.load(dto, ArticleConstans.LOADTYPE_LOAD_MORE);
    }

    @Override
    @PostMapping("/loadnew")
    public RespResult loadNew(ArticleHomeDto dto) {
        return articleHomeService.load(dto, ArticleConstans.LOADTYPE_LOAD_NEW);
    }

    @Override
    @PostMapping("/channel")
    public RespResult loadArticle(@RequestBody ArticleRequestDto dto) {
        return articleHomeService.loadArticle(dto);
    }

}