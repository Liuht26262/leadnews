package com.tanran.login.service.impl;

import java.util.Map;
import java.util.Objects;

import org.apache.curator.shaded.com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyuncs.utils.StringUtils;
import com.tanran.common.result.RespResult;
import com.tanran.login.service.LoginService;
import com.tanran.model.common.enums.ErrorCodeEnum;
import com.tanran.model.mappers.app.ApUserMapper;
import com.tanran.model.user.pojos.ApUser;
import com.tanran.utils.jwt.AppJwtUtil;
import com.tanran.utils.threadlocal.AppThreadLocalUtils;

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
    public RespResult userRegister(ApUser user) {
        if(Objects.isNull(user)){
            RespResult.errorResult(ErrorCodeEnum.PARAM_INVALID);
        }

        int i = apUserMapper.insertSelective(user);

        return RespResult.okResult(i);
    }

    @Override
    public RespResult userLogin(ApUser user) {
        //验证参数
        if(StringUtils.isEmpty(user.getPhone()) || StringUtils.isEmpty(user.getPassword())){
            return RespResult.errorResult(ErrorCodeEnum.PARAM_INVALID);
        }
        //查询用户
        ApUser dbUser = apUserMapper.selectUserByPhone(user.getPhone());
        if(dbUser==null){
            return RespResult.errorResult(ErrorCodeEnum.AP_USER_DATA_NOT_EXIST);
        }

        // 数据库密码
        String dbpassword = dbUser.getPassword();
        // String newPassword = DigestUtils.md5DigestAsHex((user.getPassword() + user.getSalt()).getBytes());
        //测试阶段，密码未加密
        String newPassword = user.getPassword();
        if (!dbpassword.equals(newPassword)) {
            return RespResult.errorResult(ErrorCodeEnum.LOGIN_PASSWORD_ERROR);
        }

        AppThreadLocalUtils.setUser(dbUser);
        System.out.println("***********已设定用户*************************");

        dbUser.setPassword("");
        Map<String,Object> map = Maps.newHashMap();
        map.put("token", AppJwtUtil.getToken(dbUser));
        map.put("user",dbUser);
        return RespResult.okResult(map);
    }
}
