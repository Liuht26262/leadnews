package com.tanran.wemedia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanran.api.wmediaApi.WmNews;
import com.tanran.common.result.RespResult;
import com.tanran.model.mappers.wemedia.WmMaterialMapper;
import com.tanran.model.media.dtos.WmNewsDto;
import com.tanran.wemedia.service.WmNewsService;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/12 16:35
 */
@RestController
@RequestMapping("/api/v1/media/news")
public class WmNewsController implements WmNews {
    @Autowired
    private WmNewsService wmNewsService;

    @Override
    @PostMapping("/submit")
    public RespResult saveNews(WmNewsDto dto) {
        return wmNewsService.saveNews(dto);
    }
}
