package com.tanran.article.loginApi;

import org.springframework.web.bind.annotation.RequestBody;

import com.tanran.common.result.RespResult;
import com.tanran.model.user.pojos.ApUser;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/3/27 12:41
 */
public interface LoginControllerApi {
    public RespResult login( ApUser user);
}
