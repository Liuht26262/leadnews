package com.tanran.api.LoginApi;

import com.tanran.common.result.RespResult;
import com.tanran.model.admin.pojos.AdUser;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/17 10:02
 */
public interface AdminLoginControllerApi {
    RespResult adminLogin(AdUser user);
}