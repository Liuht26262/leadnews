package com.tanran.api.articleApi;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanran.common.result.RespResult;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/14 11:15
 */

public interface ChannelControllerApi {
    RespResult selectAllChannels();
}
