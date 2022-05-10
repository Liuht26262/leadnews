package com.tanran.wemedia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanran.api.wmediaApi.StatisticsApi;
import com.tanran.common.result.RespResult;
import com.tanran.model.media.dtos.StatisticDto;
import com.tanran.wemedia.service.WmStatisticsService;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 图文统计接口
 * @since 2022/4/15 16:56
 */
@RestController
@RequestMapping("/api/v1/statistics")
public class WmStatisticsController implements StatisticsApi {

    @Autowired
    private WmStatisticsService wmStatisticsService;

    @PostMapping("/news")
    @Override
    public RespResult newsStatistics(@RequestBody StatisticDto dto) {
        return wmStatisticsService.newsStatistics(dto);
    }


}
