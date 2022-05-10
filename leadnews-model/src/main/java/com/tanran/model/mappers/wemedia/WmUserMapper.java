package com.tanran.model.mappers.wemedia;



import org.apache.ibatis.annotations.Param;

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

    WmUser selectByName(@Param("name") String name,@Param("roleType")String roleType);

    int deleteByPrimaryKey(Integer id);

    int insert(WmUser record);

    int insertSelective(WmUser record);

    WmUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WmUser record);

    int updateByPrimaryKey(WmUser record);

    WmUser selectWmUser(String roleType);
}
