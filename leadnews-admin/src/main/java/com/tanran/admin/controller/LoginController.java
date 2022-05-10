package com.tanran.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanran.admin.service.UserLoginService;
import com.tanran.api.LoginApi.AdminLoginControllerApi;
import com.tanran.common.result.RespResult;
import com.tanran.model.media.pojos.WmUser;


/**
 *
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/17 10:10
 */


@RestController
@RequestMapping("/login")
public class LoginController implements AdminLoginControllerApi {
    @Autowired
    private UserLoginService userLoginService;

    @Override
    @PostMapping("/in")
    public RespResult adminLogin(@RequestBody WmUser user) {
        return userLoginService.login(user);
    }
}
