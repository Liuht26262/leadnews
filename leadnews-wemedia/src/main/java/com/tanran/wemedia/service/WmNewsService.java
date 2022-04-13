package com.tanran.wemedia.service;

import com.tanran.common.result.RespResult;
import com.tanran.model.media.dtos.WmNewsDto;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/12 16:41
 */
public interface WmNewsService {
    RespResult saveNews(WmNewsDto dto);
}
