package com.tanran.behavior.service;

import com.tanran.common.result.RespResult;
import com.tanran.model.behavior.dtos.ShowBehaviorDto;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 存储用户数据业务层
 * @since 3.0.x 2022/3/18 18:24
 */
public interface BehaviorService {

    RespResult saveUserHavior(ShowBehaviorDto dto);
}
