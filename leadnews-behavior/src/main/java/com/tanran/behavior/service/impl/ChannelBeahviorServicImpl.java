package com.tanran.behavior.service.impl;

import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tanran.behavior.service.ChannelsBehaviorService;
import com.tanran.common.result.RespResult;
import com.tanran.model.common.enums.ErrorCodeEnum;
import com.tanran.model.mappers.app.ApUserChannelMapper;
import com.tanran.model.user.dtos.ChannelReqDto;
import com.tanran.model.user.pojos.ApUserChannel;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/5/5 11:17
 */
@Service
@Slf4j
public class ChannelBeahviorServicImpl implements ChannelsBehaviorService {

    @Autowired
    private ApUserChannelMapper userChannelMapper;

    @Override
    public RespResult addUserChannel(ChannelReqDto dto) {
        if(Objects.isNull(dto)||dto.getUserId() == null){
            System.out.println(dto);
            return RespResult.errorResult(ErrorCodeEnum.PARAM_INVALID);
        }
        ApUserChannel apUserChannel = new ApUserChannel();
        apUserChannel.setChannelId(dto.getId());
        apUserChannel.setChannelName(dto.getName());
        apUserChannel.setUserId(dto.getUserId());
        apUserChannel.setCreatedTime(new Date(System.currentTimeMillis()));
        int i = userChannelMapper.insertSelective(apUserChannel);
        if(i>=0){
            log.info("===========订阅"+dto.getName()+"频道成功==========");
            return RespResult.okResult(ErrorCodeEnum.SUCCESS);
        }
        return RespResult.errorResult(ErrorCodeEnum.SERVER_ERROR,"操作失败");
    }

    @Override
    public RespResult deleteChannel(Integer userId, Integer channelId) {
        if(userId == null || channelId == null){
            return RespResult.errorResult(ErrorCodeEnum.PARAM_REQUIRE);
        }
        int i = userChannelMapper.deleteUserChannel(userId,channelId);
        if(i>=0){
            log.info("========取消订阅成功===========");
            return RespResult.okResult(ErrorCodeEnum.SUCCESS);
        }
        return RespResult.errorResult(ErrorCodeEnum.SERVER_ERROR,"操作失败");
    }
}
