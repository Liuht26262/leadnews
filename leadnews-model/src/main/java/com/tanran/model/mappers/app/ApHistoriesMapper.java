package com.tanran.model.mappers.app;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tanran.model.behavior.pojos.ApHistories;
import com.tanran.model.common.dtos.PageRequestDto;

public interface ApHistoriesMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ApHistories record);

    int insertSelective(ApHistories record);

    ApHistories selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ApHistories record);

    int updateByPrimaryKey(ApHistories record);

    List<ApHistories> selectArticleList(@Param("userId") Integer userId, @Param("dto") PageRequestDto dto);
}
