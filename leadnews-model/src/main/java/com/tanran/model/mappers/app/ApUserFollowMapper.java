package com.tanran.model.mappers.app;

import org.apache.ibatis.annotations.Param;

import com.tanran.model.user.pojos.ApUserFollow;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 3.0.x 2022/3/23 11:22
 */
public interface ApUserFollowMapper {
    public ApUserFollow selectUserFollowByFollowId(@Param("userId") Long userId,@Param("followId") Integer followId);

    int insertUserFollow(ApUserFollow userFollow);

    int deleteUserFollow(@Param("id") Long id, @Param("follow") Integer followId);
}
