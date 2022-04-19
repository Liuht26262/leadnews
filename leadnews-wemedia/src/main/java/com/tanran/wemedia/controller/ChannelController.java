package com.tanran.wemedia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanran.api.articleApi.ChannelControllerApi;

import com.tanran.common.result.RespResult;
import com.tanran.wemedia.service.ChannelService;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/14 11:17
 */
@RestController
@RequestMapping("/api/v1/channel")
public class ChannelController implements ChannelControllerApi {
    @Autowired
    private ChannelService channelService;

    @RequestMapping("/channels")
    @Override
    public RespResult selectAllChannels() {
        return channelService.selectAllChannel();
    }
}
