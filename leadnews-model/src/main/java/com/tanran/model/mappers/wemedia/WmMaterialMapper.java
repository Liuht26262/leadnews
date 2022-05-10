package com.tanran.model.mappers.wemedia;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tanran.model.media.dtos.WmMaterialListDto;
import com.tanran.model.media.pojos.WmMaterial;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/12 16:14
 */
public interface WmMaterialMapper {
    /**
     * 查询所有
     */
    List<WmMaterial> findMaterialByUidAndimgUrls(Long uid, Collection<Object> values);

    int deleteByPrimaryKey(Integer id);

    int insert(WmMaterial record);

    int insertSelective(WmMaterial record);

    WmMaterial selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WmMaterial record);

    int updateByPrimaryKey(WmMaterial record);

    List<WmMaterial> findListByUidAndStatus(@Param("dto") WmMaterialListDto dto, Long uid);

    int countListByUidAndStatus(WmMaterialListDto dto, Long uid);

}
