package com.tanran.model.mappers.app;

import com.tanran.model.behavior.pojos.ApLikesBehavior;
import com.tanran.model.behavior.pojos.ApUnlikesBehavior;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 3.0.x 2022/3/20 17:02
 */
public interface LikeBehaviorMapper {

    /**
     * 选择最后一条喜欢按钮
     * @return
     */
    ApLikesBehavior selectLastLike(Integer objectId,Integer entryId,Short type);

    /**
     * 选择最后一条不喜欢数据
     * @return
     */
    ApUnlikesBehavior selectLastUnLike(Integer entryId,Integer articleId);

}
