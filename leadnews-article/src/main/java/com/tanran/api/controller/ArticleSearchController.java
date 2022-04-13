package com.tanran.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanran.api.articleApi.ArticleSearchControllerApi;
import com.tanran.api.service.ArticleSearchService;
import com.tanran.common.result.RespResult;
import com.tanran.model.article.dtos.UserSearchDto;
import com.tanran.model.user.pojos.ApUserSearch;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/3/31 9:16
 */
@RestController
@RequestMapping("/api/v1/article/search")
public class ArticleSearchController implements ArticleSearchControllerApi {
    @Autowired
    private ArticleSearchService articleSearchService;

    @PostMapping("/load_search_history")
    @Override
    public RespResult<List<ApUserSearch>> selectUserSearch(@RequestBody UserSearchDto dto) {
        return articleSearchService.selectUserSearch(dto);
    }

    @PostMapping("/del_search")
    @Override
    public RespResult deleteUserSearch(@RequestBody UserSearchDto dto) {
        return articleSearchService.deleteUserSearch(dto);
    }

    @PostMapping("/clear_search")
    @Override
    public RespResult clearUserSearch(@RequestBody UserSearchDto dto) {
        return articleSearchService.clearUserSearch(dto);
    }

    @PostMapping("/load_hot_keywords")
    @Override
    public RespResult loadHotKeyWords(@RequestBody UserSearchDto dto) {
        return articleSearchService.loadHotKeyWords(dto);
    }

    @PostMapping("/associate_search")
    @Override
    public RespResult selectAssociateWords(@RequestBody UserSearchDto dto) {
        return articleSearchService.selectAssociateWords(dto);
    }

    @PostMapping("/article_search")
    @Override
    public RespResult articleSearch(@RequestBody UserSearchDto dto) {
        return articleSearchService.articleSearch(dto);
    }
}
