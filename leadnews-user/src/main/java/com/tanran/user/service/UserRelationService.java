package com.tanran.user.service;

import com.tanran.common.result.RespResult;
import com.tanran.model.user.dtos.UserRelationDto;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 3.0.x 2022/3/22 19:39
 */
public interface UserRelationService {

    RespResult userFollow(UserRelationDto userRelationDto);
}
