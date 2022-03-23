package com.tanran.model.mappers.app;

import org.apache.ibatis.annotations.Param;

import com.tanran.model.user.pojos.ApUserFan;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 3.0.x 2022/3/23 10:51
 */
public interface ApUserFanMapper {
    public ApUserFan slectUserFanByFanId(@Param("userId") Integer userId,@Param("fansId")Long fansId);

    int insertUserFan(ApUserFan userFan);

    int deleteFan(@Param("followId") Integer followId, @Param("userId") Long userId);
}
