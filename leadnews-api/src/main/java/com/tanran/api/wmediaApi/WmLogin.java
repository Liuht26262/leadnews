package com.tanran.api.wmediaApi;

import com.tanran.common.result.RespResult;
import com.tanran.model.media.pojos.WmUser;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/12 15:00
 */
public interface WmLogin {
    RespResult LoginWmUser(WmUser user);
}
