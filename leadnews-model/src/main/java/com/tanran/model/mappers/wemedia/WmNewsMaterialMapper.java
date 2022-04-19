package com.tanran.model.mappers.wemedia;

import java.util.List;
import java.util.Map;

import com.tanran.model.media.pojos.WmNewsMaterial;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/12 17:10
 */
public interface WmNewsMaterialMapper {

    void saveRelationsByContent(Map<String, Object> materials, Integer newsId,Short type);

    int deleteByPrimaryKey(Integer id);

    int insert(WmNewsMaterial record);

    int insertSelective(WmNewsMaterial record);

    WmNewsMaterial selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WmNewsMaterial record);

    int updateByPrimaryKey(WmNewsMaterial record);

    List<WmNewsMaterial> selectWmByMaterialId(Integer MaterialId);

    int deleteByUserId(Integer NewsId);
}
