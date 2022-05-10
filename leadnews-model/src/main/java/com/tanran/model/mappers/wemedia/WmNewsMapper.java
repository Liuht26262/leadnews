package com.tanran.model.mappers.wemedia;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tanran.model.media.dtos.WmNewsPageReqDto;
import com.tanran.model.media.pojos.WmNews;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/12 16:49
 */
public interface WmNewsMapper {
    /**
     * 根据主键修改
     * @param
     * @return
     */
    int updateByPrimaryKey(WmNews record);

    /**
     * 添加草稿新闻
     * @param dto
     * @return
     */
    int insertNewsForEdit(WmNews dto);

    int insert(WmNews dto);

    List<WmNews> selectAllNews(@Param("dto") WmNewsPageReqDto dto,@Param("uid") Long uid);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(WmNews record);

    WmNews selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WmNews record);

    int updateByPrimaryKeyWithBLOBs(WmNews record);

    int countSelectBySelective(@Param("dto") WmNewsPageReqDto dto,@Param("uid") Long uid);
}
