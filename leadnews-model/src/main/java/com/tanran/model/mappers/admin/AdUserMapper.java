package com.tanran.model.mappers.admin;

import com.tanran.model.admin.pojos.AdUser;

/**
 *
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/17 10:45
 */


public interface AdUserMapper {

    AdUser selectByName(String name);
}