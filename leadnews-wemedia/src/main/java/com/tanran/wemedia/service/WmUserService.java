package com.tanran.wemedia.service;

import com.tanran.common.result.RespResult;
import com.tanran.model.media.pojos.WmUser;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/12 14:30
 */
public interface WmUserService {
    RespResult LoginWmUser(WmUser user);

    RespResult getUserFile();
}
