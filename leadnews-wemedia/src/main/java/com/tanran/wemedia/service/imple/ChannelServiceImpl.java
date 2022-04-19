package com.tanran.wemedia.service.imple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.tanran.common.result.RespResult;
import com.tanran.model.admin.pojos.AdChannel;
import com.tanran.model.mappers.wemedia.ChannelMapper;
import com.tanran.wemedia.service.ChannelService;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/14 11:19
 */
@Service
public class ChannelServiceImpl implements ChannelService {
    @Autowired
    private ChannelMapper channelMapper;

    @Override
    public RespResult selectAllChannel() {
        List<AdChannel> adChannels = channelMapper.selectAllChannel();
        return RespResult.okResult(adChannels);
    }
}
