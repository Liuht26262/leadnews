package com.tanran.model.mappers.wemedia;

import java.util.Collection;
import java.util.List;
import java.util.Map;


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

    int insert(WmMaterial record);

}
