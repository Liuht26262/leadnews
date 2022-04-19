package com.tanran.api.wmediaApi;

import com.tanran.common.result.RespResult;
import com.tanran.model.common.dtos.PageResponseResult;
import com.tanran.model.media.dtos.WmNewsDto;
import com.tanran.model.media.dtos.WmNewsPageReqDto;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/12 16:32
 */
public interface WmNews {
    RespResult saveNews(WmNewsDto dto);

    RespResult saveDraftNews(WmNewsDto dto);

    PageResponseResult selectAllNew(WmNewsPageReqDto reqDto);

    RespResult deleteNews(WmNewsDto dto);

    RespResult selectNewById(WmNewsDto dto);
}
