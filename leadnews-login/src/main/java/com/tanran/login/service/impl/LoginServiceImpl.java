package com.tanran.login.service.impl;

import java.util.Map;

import org.apache.curator.shaded.com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyuncs.utils.StringUtils;
import com.tanran.common.result.RespResult;
import com.tanran.login.service.LoginService;
import com.tanran.model.common.enums.AppHttpCodeEnum;
import com.tanran.model.mappers.app.ApUserMapper;
import com.tanran.model.user.pojos.ApUser;
import com.tanran.utils.jwt.AppJwtUtil;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/3/25 19:14
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private ApUserMapper apUserMapper;

    @Override
    public RespResult userLogin(ApUser user) {
        //验证参数
        if(StringUtils.isEmpty(user.getPhone()) || StringUtils.isEmpty(user.getPassword())){
            return RespResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //查询用户
        ApUser dbUser = apUserMapper.selectUserByPhone(user.getPhone());
        if(dbUser==null){
            return RespResult.errorResult(AppHttpCodeEnum.AP_USER_DATA_NOT_EXIST);
        }
        //密码错误
        if(!user.getPassword().equals(dbUser.getPassword())){
            return RespResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
        }
        dbUser.setPassword("");
        Map<String,Object> map = Maps.newHashMap();
        map.put("token", AppJwtUtil.getToken(dbUser));
        map.put("user",dbUser);
        return RespResult.okResult(map);
    }
}
