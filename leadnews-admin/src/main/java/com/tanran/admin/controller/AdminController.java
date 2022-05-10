package com.tanran.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanran.common.result.RespResult;
import com.tanran.model.common.constants.WmMediaConstans;
import com.tanran.model.common.dtos.PageResponseResult;
import com.tanran.model.media.dtos.WmNewsDto;
import com.tanran.model.media.dtos.WmNewsPageReqDto;
import com.tanran.utils.threadlocal.WmThreadLocalUtils;
import com.tanran.wemedia.service.ChannelService;
import com.tanran.wemedia.service.WmNewsService;
import com.tanran.wemedia.service.imple.ChannelServiceImpl;
import com.tanran.wemedia.service.imple.WmNewsServiceImpl;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/5/7 14:49
 */
@RestController
@RequestMapping("/api/v1")
public class AdminController {
    @Autowired(required = false)
    private WmNewsService wmNewsService;
    @Autowired(required = false)
    private ChannelService channelService;

    @PostMapping("/media/news/submit")
    public RespResult saveNews(@RequestBody WmNewsDto dto) {
        return new WmNewsServiceImpl().saveNews(dto, WmMediaConstans.WM_NEWS_SUMMIT_STATUS);
    }

    @PostMapping("/media/news/save_draft")
    public RespResult saveDraftNews(@RequestBody WmNewsDto dto) {
        return wmNewsService.saveNews(dto, WmMediaConstans.WM_NEWS_DRAFT_STATUS);
    }

    @PostMapping("/media/news/list")
    public PageResponseResult selectAllNew(@RequestBody WmNewsPageReqDto reqDto) {
        System.out.println(reqDto);
        System.out.println("此时的用户"+ WmThreadLocalUtils.getUser());
        return new WmNewsServiceImpl().selectAllNews(reqDto);
    }

    @PostMapping("/media/news/del_news")
    public RespResult deleteNews(@RequestBody WmNewsDto dto) {
        return wmNewsService.deleteNews(dto);
    }


    @PostMapping("/media/news/news")
    public RespResult selectNewById(@RequestBody WmNewsDto dto) {
        return RespResult.okResult(wmNewsService.selectNewById(dto.getId()));
    }

    @RequestMapping("/channel/channels")
    public RespResult selectAllChannels() {

        return new ChannelServiceImpl().selectAllChannel();
    }
}
