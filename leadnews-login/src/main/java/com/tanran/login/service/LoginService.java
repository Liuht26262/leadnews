package com.tanran.login.service;

import com.tanran.common.result.RespResult;
import com.tanran.model.user.pojos.ApUser;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 用户登录接口
 * @since 2022/3/25 19:13
 */
public interface LoginService {
    RespResult userLogin(ApUser user);
}
