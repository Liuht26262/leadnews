package com.tanran.model.mappers.wemedia;

import java.util.List;

import com.tanran.model.admin.pojos.AdChannel;

public interface ChannelMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(AdChannel record);

    int insertSelective(AdChannel record);

    AdChannel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdChannel record);

    int updateByPrimaryKey(AdChannel record);

    List<AdChannel> selectAllChannel();
}