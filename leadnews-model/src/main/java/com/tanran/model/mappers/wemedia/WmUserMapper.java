package com.tanran.model.mappers.wemedia;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.tanran.model.media.pojos.WmUser;


/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/12 14:01
 */
public interface WmUserMapper {

    WmUser selectByName(@Param("name") String name);

}
