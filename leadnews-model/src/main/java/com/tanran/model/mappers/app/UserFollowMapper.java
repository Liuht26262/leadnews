package com.tanran.model.mappers.app;

import com.tanran.model.user.pojos.ApUserFollow;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 用户关注信息查询
 * @since 3.0.x 2022/3/20 16:56
 */
public interface UserFollowMapper {
        ApUserFollow selectByFollowId(Long userId,Integer followId);
    }

