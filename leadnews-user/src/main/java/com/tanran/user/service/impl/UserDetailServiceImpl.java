package com.tanran.user.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tanran.common.result.RespResult;
import com.tanran.model.common.enums.ErrorCodeEnum;
import com.tanran.model.mappers.app.ApUserChannelMapper;
import com.tanran.model.mappers.app.ApUserMapper;
import com.tanran.model.user.pojos.ApUser;
import com.tanran.model.user.pojos.ApUserChannel;
import com.tanran.user.service.UserDetailService;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/25 11:40
 */
@Service
public class UserDetailServiceImpl implements UserDetailService {
    @Autowired
    private ApUserMapper userMapper;
    @Autowired
    private ApUserChannelMapper apUserChannelMapper;
    @Override
    public RespResult selectUserById(Long id) {
        return RespResult.okResult(userMapper.selectUserById(id));
    }

    @Override
    public RespResult selectChannelByUser(Long id) {
        List<ApUserChannel> apUserChannels = apUserChannelMapper.selectChannelsByUser(id);
        System.out.println(apUserChannels);
        return RespResult.okResult(apUserChannels);
    }

    @Override
    public RespResult updateUserProfile(ApUser user) {
        if(Objects.isNull(user)){
            return RespResult.errorResult(ErrorCodeEnum.PARAM_INVALID);
        }
        int i = userMapper.updateByPrimaryKeySelective(user);
        return RespResult.okResult(i);
    }
}
