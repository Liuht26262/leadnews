package com.tanran.api.behaviorApi;

import com.tanran.common.result.RespResult;
import com.tanran.model.behavior.dtos.CollectionBehaviorDto;
import com.tanran.model.behavior.dtos.UnLikesBehaviorDto;
import com.tanran.model.user.dtos.ChannelReqDto;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 3.0.x 2022/3/23 19:16
 */
public interface UserBehaviorControllerApi {
    RespResult SaveUnLikeBehavior(UnLikesBehaviorDto dto);

    RespResult collectionBehavior(CollectionBehaviorDto dto);

    RespResult addUserChannel(ChannelReqDto dto);

    RespResult deleteChannel(Integer userId,Integer channelId);

}
