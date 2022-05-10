package com.tanran.wemedia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanran.api.wmediaApi.WmNews;
import com.tanran.common.result.RespResult;
import com.tanran.model.common.constants.WmMediaConstans;
import com.tanran.model.common.dtos.PageResponseResult;
import com.tanran.model.media.dtos.WmNewsDto;
import com.tanran.model.media.dtos.WmNewsPageReqDto;
import com.tanran.wemedia.service.WmNewsService;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 文章发布
 * @since 2022/4/12 16:35
 */
@RestController
@RequestMapping("/api/v1/media/news")
public class WmNewsController implements WmNews {
    @Autowired
    private WmNewsService wmNewsService;

    @Override
    @PostMapping("/submit")
    public RespResult saveNews(@RequestBody WmNewsDto dto) {
        return wmNewsService.saveNews(dto, WmMediaConstans.WM_NEWS_SUMMIT_STATUS);
    }

    @Override
    @PostMapping("/save_draft")
    public RespResult saveDraftNews(@RequestBody WmNewsDto dto) {
        return wmNewsService.saveNews(dto, WmMediaConstans.WM_NEWS_DRAFT_STATUS);
    }

    @Override
    @PostMapping("/list")
    public PageResponseResult selectAllNew(@RequestBody WmNewsPageReqDto reqDto) {
        System.out.println(reqDto);
        return wmNewsService.selectAllNews(reqDto);
    }

    @Override
    @PostMapping("/del_news")
    public RespResult deleteNews(@RequestBody WmNewsDto dto) {
        return wmNewsService.deleteNews(dto);
    }

    @Override
    @PostMapping("/news")
    public RespResult selectNewById(@RequestBody WmNewsDto dto) {
        return RespResult.okResult(wmNewsService.selectNewById(dto.getId()));
    }

    @Override
    @PostMapping("down_or_up")
    public RespResult articleUpOtDown(@RequestBody WmNewsDto dto) {
        return wmNewsService.articleUpOtDown(dto);
    }

}
