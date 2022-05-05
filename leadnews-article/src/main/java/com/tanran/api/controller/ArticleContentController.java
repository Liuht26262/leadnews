package com.tanran.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tanran.api.articleApi.ArticleContentControllerApi;
import com.tanran.api.service.ArticleContentService;
import com.tanran.api.service.LoadArticleBehaviorService;
import com.tanran.common.result.RespResult;
import com.tanran.model.article.dtos.ArticleInfoDto;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 文章详情接口
 * @since 3.0.x 2022/3/19 22:24
 */
@RestController
@RequestMapping("/api/v1/article")
public class ArticleContentController implements ArticleContentControllerApi {

    @Autowired
    private ArticleContentService articleContentService;
    @Autowired
    private LoadArticleBehaviorService loadArticleBehaviorService;

    @Override
    @GetMapping("/{articleId}")
    public RespResult loadArticleContent(@RequestParam("articleId") Integer articleId,@RequestParam("userId") Integer userId) {

        return articleContentService.getArticleContent(articleId,userId);
    }

    @Override
    @PostMapping("/load_article_behavior")
    public RespResult loadArticleBehavior(ArticleInfoDto dto) {

        return loadArticleBehaviorService.loadArticleBehavior(dto);
    }
}
