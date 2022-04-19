package com.tanran.admin.service.impl;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import com.tanran.admin.service.UserLoginService;
import com.tanran.common.result.RespResult;
import com.tanran.model.admin.pojos.AdUser;
import com.tanran.model.common.dtos.ResponseResult;
import com.tanran.model.common.enums.ErrorCodeEnum;
import com.tanran.model.mappers.admin.AdUserMapper;
import com.tanran.utils.jwt.AppJwtUtil;


/**
 *
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/17 10:25
 */

@Service
@SuppressWarnings("all")
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private AdUserMapper adUserMapper;

    @Override
    public RespResult login(AdUser user) {
        if(StringUtils.isEmpty(user.getName())&&StringUtils.isEmpty(user.getPassword())){
            return RespResult.errorResult(ErrorCodeEnum.PARAM_REQUIRE,"用户和密码不能为空");
        }
        AdUser adUser = adUserMapper.selectByName(user.getName());
        if(adUser==null){
            return RespResult.errorResult(ErrorCodeEnum.DATA_NOT_EXIST,"用户不存在");
        }else{
            if(user.getPassword().equals(adUser.getPassword())){
                Map<String,Object> map = new HashMap<>();
                adUser.setPassword("");
                adUser.setSalt("");
                map.put("token", AppJwtUtil.getToken(adUser));
                map.put("user",adUser);
                return RespResult.okResult(map);
            }else{
                return RespResult.errorResult(ErrorCodeEnum.LOGIN_PASSWORD_ERROR);
            }
        }
    }
}
