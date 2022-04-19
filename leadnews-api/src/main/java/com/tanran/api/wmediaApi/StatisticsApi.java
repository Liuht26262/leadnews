package com.tanran.api.wmediaApi;


import com.tanran.common.result.RespResult;
import com.tanran.model.media.dtos.StatisticDto;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/15 16:51
 */
public interface StatisticsApi {
    RespResult newsStatistics(StatisticDto dto);

}
