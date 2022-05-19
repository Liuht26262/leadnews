package com.tanran.model.mappers.wemedia;

import java.util.List;

import com.tanran.model.media.pojos.AdSensitive;

public interface AdSensitiveMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(AdSensitive record);

    int insertSelective(AdSensitive record);


    AdSensitive selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdSensitive record);

    int updateByPrimaryKey(AdSensitive record);

    List<String> selectAllSensitive();
}