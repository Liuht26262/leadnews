package com.tanran.behavior.service;

import com.tanran.common.result.RespResult;
import com.tanran.model.behavior.dtos.LikesBehaviorDto;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 3.0.x 2022/3/23 19:29
 */
public interface LikeBehaviorService {
    RespResult saveLikeBehavior(LikesBehaviorDto dto);
}
