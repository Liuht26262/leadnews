package com.tanran.admin.service;

import com.tanran.common.result.RespResult;
import com.tanran.model.media.pojos.WmUser;

/**
 *
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/17 10:15
 */

public interface UserLoginService {

    /**
     * 登录
     * @param user
     * @return
     */
    public RespResult login(WmUser user);
}
