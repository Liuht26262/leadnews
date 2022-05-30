package com.tanran.model.mappers.wemedia;

import java.util.List;

import com.tanran.model.media.pojos.SendLog;

public interface SendLogMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SendLog record);

    int insertSelective(SendLog record);

    SendLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SendLog record);

    int updateByPrimaryKey(SendLog record);

    List<SendLog> selectAll();
}