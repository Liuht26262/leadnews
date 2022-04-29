package com.tanran.article.behaviorApi;

import com.tanran.common.result.RespResult;
import com.tanran.model.behavior.dtos.UnLikesBehaviorDto;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 3.0.x 2022/3/23 19:16
 */
public interface UnLikeBehaviorControllerApi {
    RespResult SaveUnLikeBehavior(UnLikesBehaviorDto dto);
}
