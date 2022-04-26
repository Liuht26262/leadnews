package com.tanran.model.mappers.app;

import java.util.List;

import com.tanran.model.user.pojos.ApUserChannel;

public interface ApUserChannelMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ApUserChannel record);

    int insertSelective(ApUserChannel record);

    ApUserChannel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ApUserChannel record);

    int updateByPrimaryKey(ApUserChannel record);

    List<ApUserChannel> selectChannelsByUser(Long id);

}