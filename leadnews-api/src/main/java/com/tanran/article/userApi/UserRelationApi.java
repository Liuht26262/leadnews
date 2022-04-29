package com.tanran.article.userApi;

import com.tanran.common.result.RespResult;
import com.tanran.model.user.dtos.UserRelationDto;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 此接口用于实现当前用户关注其它用户的记录
 * @since 3.0.x 2022/3/22 19:27
 */

public interface UserRelationApi {
    public RespResult userFollow(UserRelationDto userRelationDto);
}
