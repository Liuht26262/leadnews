package com.tanran.behavior.service;

import com.tanran.common.result.RespResult;
import com.tanran.model.behavior.dtos.ReadBehaviorDto;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 文章详情阅读行为数据储存
 * @since 3.0.x 2022/3/23 20:14
 */
public interface ReadBehaviorService {
    RespResult saveReadBehavior(ReadBehaviorDto dto);
}
