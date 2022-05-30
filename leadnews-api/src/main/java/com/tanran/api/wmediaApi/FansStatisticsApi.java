package com.tanran.api.wmediaApi;

import com.tanran.common.result.RespResult;
import com.tanran.model.common.dtos.PageRequestDto;
import com.tanran.model.common.dtos.PageResponseResult;
import com.tanran.model.media.dtos.FansReqDto;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/18 20:07
 */
public interface FansStatisticsApi {
    PageResponseResult fansStatistics(PageRequestDto dto);

    RespResult fansList(FansReqDto dto);

    RespResult cancelFollow(FansReqDto dto);

    RespResult loadFansInfo(String fansId);
}
