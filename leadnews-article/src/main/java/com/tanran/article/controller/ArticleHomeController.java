package com.tanran.article.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanran.article.articleApi.ArticleHomeControllerApi;
import com.tanran.article.service.ArticleHomeService;
import com.tanran.common.constans.ArticleConstans;
import com.tanran.common.result.RespResult;
import com.tanran.model.article.dtos.ArticleHomeDto;

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
    @GetMapping("/load")
    public RespResult load(ArticleHomeDto dto) {
        return articleHomeService.load(dto, ArticleConstans.LOADTYPE_LOAD_MORE);
    }

    @Override
    @GetMapping("/loadmore")
    public RespResult loadMore(ArticleHomeDto dto) {
        return articleHomeService.load(dto, ArticleConstans.LOADTYPE_LOAD_MORE);
    }

    @Override
    @GetMapping("/loadnew")
    public RespResult loadNew(ArticleHomeDto dto) {
        return articleHomeService.load(dto, ArticleConstans.LOADTYPE_LOAD_NEW);
    }

}
