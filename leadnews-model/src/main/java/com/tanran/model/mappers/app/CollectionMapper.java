package com.tanran.model.mappers.app;

import com.tanran.model.article.pojos.ApCollection;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 收藏查询
 * @since 3.0.x 2022/3/20 16:34
 */
public interface CollectionMapper {
    ApCollection selectCollectionForEntryId(Integer objectId,Integer entryId,Short type);
}
