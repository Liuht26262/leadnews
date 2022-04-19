package com.tanran.model.mappers.wemedia;


import com.tanran.model.media.dtos.StatisticDto;
import com.tanran.model.media.pojos.WmNewsStatistics;

public interface NewsStatisticsMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(WmNewsStatistics record);

    int insertSelective(WmNewsStatistics record);

    WmNewsStatistics selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WmNewsStatistics record);

    int updateByPrimaryKey(WmNewsStatistics record);

    WmNewsStatistics selectNewStatistics(StatisticDto dto);
}