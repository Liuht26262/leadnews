package com.tanran.wemedia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanran.api.wmediaApi.WmLogin;
import com.tanran.common.result.RespResult;
import com.tanran.model.media.pojos.WmUser;
import com.tanran.model.media.pojos.WmUserLogin;
import com.tanran.wemedia.service.WmUserService;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/12 13:59
 */
@RestController
@RequestMapping("/api/v1/user")
public class WmLogiController implements WmLogin {

    @Autowired
    private WmUserService wmService;

    @Override
    @PostMapping("/login/in")
    public RespResult LoginWmUser(@RequestBody WmUser user){
        return wmService.LoginWmUser(user);
    }

    @Override
    @PostMapping("/profile")
    public RespResult userProfile() {
        return wmService.getUserFile();
    }

}
