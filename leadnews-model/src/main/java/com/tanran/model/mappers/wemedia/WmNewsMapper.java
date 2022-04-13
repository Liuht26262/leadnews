package com.tanran.model.mappers.wemedia;

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
}
