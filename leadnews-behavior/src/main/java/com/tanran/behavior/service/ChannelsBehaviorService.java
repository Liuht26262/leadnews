package com.tanran.behavior.service;

import com.tanran.common.result.RespResult;
import com.tanran.model.user.dtos.ChannelReqDto;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/5/5 11:16
 */
public interface ChannelsBehaviorService {
    RespResult addUserChannel(ChannelReqDto dto);

    RespResult deleteChannel(Integer userId, Integer channelId);
}
