package com.tanran.model.mappers.app;

import com.tanran.model.user.pojos.ApUser;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 3.0.x 2022/3/23 10:51
 */

public interface ApUserMapper {
    ApUser selectUserById( Long id);

    ApUser selectUserByPhone( String phone);

    int deleteByPrimaryKey(Long id);

    int insert(ApUser record);

    int insertSelective(ApUser record);

    ApUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApUser record);

    int updateByPrimaryKey(ApUser record);

}
