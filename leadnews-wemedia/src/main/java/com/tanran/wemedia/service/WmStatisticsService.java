package com.tanran.wemedia.service;

import com.tanran.common.result.RespResult;
import com.tanran.model.common.dtos.PageRequestDto;
import com.tanran.model.common.dtos.PageResponseResult;
import com.tanran.model.media.dtos.FansReqDto;
import com.tanran.model.media.dtos.StatisticDto;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/15 16:57
 */
public interface WmStatisticsService {

    RespResult newsStatistics(StatisticDto dto);

    PageResponseResult fansStatistics(PageRequestDto dto);

    RespResult fansList(FansReqDto dto);

    RespResult cancelFollow(FansReqDto dto);
}
