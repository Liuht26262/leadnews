package com.tanran.wemedia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanran.api.wmediaApi.FansStatisticsApi;
import com.tanran.model.common.dtos.PageRequestDto;
import com.tanran.model.common.dtos.PageResponseResult;
import com.tanran.wemedia.service.WmStatisticsService;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/18 20:08
 */
@RestController
@RequestMapping("/api/v1/user_fans")
public class FanStatisticesController implements FansStatisticsApi{

    @Autowired
    private WmStatisticsService wmStatisticsService;

    @PostMapping("/list")
    @Override
    public PageResponseResult fansStatistics(@RequestBody PageRequestDto dto) {
        return wmStatisticsService.fansStatistics(dto);
    }
}
