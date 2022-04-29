package com.tanran.behavior.service;

import com.tanran.common.result.RespResult;
import com.tanran.model.behavior.dtos.FollowBehaviorDto;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 3.0.x 2022/3/23 15:13
 */
public interface FollowBehaviorService {
    RespResult save(FollowBehaviorDto dto);
}
