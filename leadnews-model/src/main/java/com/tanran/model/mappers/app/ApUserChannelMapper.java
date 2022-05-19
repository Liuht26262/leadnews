package com.tanran.model.mappers.app;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tanran.model.user.pojos.ApUserChannel;

public interface ApUserChannelMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ApUserChannel record);

    int insertSelective(ApUserChannel record);

    ApUserChannel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ApUserChannel record);

    int updateByPrimaryKey(ApUserChannel record);

    List<ApUserChannel> selectChannelsByUser(Long id);

    int deleteUserChannel(@Param("userId") Integer userId, @Param("channelId") Integer channelId);

    List<ApUserChannel> selectByChannelId(Integer channelId);
}