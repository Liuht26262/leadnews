package com.tanran.user.service;

import com.tanran.common.result.RespResult;
import com.tanran.model.user.pojos.ApUser;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/25 11:33
 */
public interface UserDetailService {
    RespResult selectUserById(Long id);

    RespResult selectChannelByUser(Long id);

    RespResult updateUserProfile(ApUser user);
}
