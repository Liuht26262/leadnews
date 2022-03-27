package com.tanran.api.LoginApi;

import com.tanran.common.result.RespResult;
import com.tanran.model.user.pojos.ApUser;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/3/25 19:20
 */
public interface LoginControllerApi {
    RespResult UserLogin(ApUser user);
}
