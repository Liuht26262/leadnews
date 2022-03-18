package com.tanran.api.behaviorApi;

import com.tanran.common.result.RespResult;
import com.tanran.model.behavior.dtos.ShowBehaviorDto;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 用户行为接口
 * @since 3.0.x 2022/3/18 18:05
 */
public interface BehaviorControllerApi {
    RespResult saveUserHavior(ShowBehaviorDto dto);
}
