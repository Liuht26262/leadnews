package com.tanran.behavior.service;

import com.tanran.common.result.RespResult;
import com.tanran.model.behavior.dtos.CollectionBehaviorDto;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/5/4 16:32
 */
public interface CollectionBehaviorService {
    RespResult collectionBehavior(CollectionBehaviorDto dto);
}
