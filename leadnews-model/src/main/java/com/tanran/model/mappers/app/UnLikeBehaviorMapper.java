package com.tanran.model.mappers.app;

import com.tanran.model.behavior.dtos.UnLikesBehaviorDto;
import com.tanran.model.behavior.pojos.ApUnlikesBehavior;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 3.0.x 2022/3/23 17:34
 */
public interface UnLikeBehaviorMapper {
    int SaveUnLikeBeHaviorMapper(ApUnlikesBehavior behavior);
    int deleteUnLikeBehaviorMapper(Integer entryId,Integer article);
    ApUnlikesBehavior selectUnLikeByEntryId(Integer entryId,Integer article);

}
