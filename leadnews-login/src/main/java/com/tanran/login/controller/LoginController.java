package com.tanran.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanran.api.LoginApi.LoginControllerApi;
import com.tanran.common.result.RespResult;
import com.tanran.login.service.LoginService;
import com.tanran.model.user.pojos.ApUser;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/3/25 19:18
 */
@RestController
@RequestMapping("/api/v1/login")
public class LoginController implements LoginControllerApi {

    @Autowired
    private LoginService loginService;

    @Override
    @PostMapping("/login_auth")
    public RespResult UserLogin(@RequestBody ApUser user) {
        return loginService.userLogin(user);
    }

    /**
     * 注册*/
    @Override
    @PostMapping("/register")
    public RespResult userregister(@RequestBody ApUser user) {
        return loginService.userRegister(user);
    }

}
