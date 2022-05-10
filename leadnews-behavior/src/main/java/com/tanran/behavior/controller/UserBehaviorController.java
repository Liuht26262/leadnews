package com.tanran.behavior.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tanran.api.behaviorApi.UserBehaviorControllerApi;
import com.tanran.behavior.service.ChannelsBehaviorService;
import com.tanran.behavior.service.CollectionBehaviorService;
import com.tanran.behavior.service.UnLikeBehaviorService;
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
 * @since 3.0.x 2022/3/23 19:18
 */
@RestController
@RequestMapping("/api/v1/behavior")
public class UserBehaviorController implements UserBehaviorControllerApi {

    @Autowired
    private UnLikeBehaviorService unLikeBehaviorService;
    @Autowired
    private CollectionBehaviorService collectionBehaviorService;
    @Autowired
    private ChannelsBehaviorService channelsBehaviorService;

    @PostMapping("/unlike_behavior")
    @Override
    public RespResult SaveUnLikeBehavior(@RequestBody UnLikesBehaviorDto dto) {
        return unLikeBehaviorService.SaveUnLikeBehavior(dto);
    }

    @PostMapping("/collection")
    @Override
    public RespResult collectionBehavior(@RequestBody CollectionBehaviorDto dto) {
        return collectionBehaviorService.collectionBehavior(dto);
    }

    @PatchMapping("/channels")
    @Override
    public RespResult addUserChannel(@RequestBody ChannelReqDto dto) {
        return channelsBehaviorService.addUserChannel(dto);
    }

    @DeleteMapping("/delete")
    @Override
    public RespResult deleteChannel(@RequestParam("userId") Integer userId, @RequestParam("id") Integer channelId) {
        return channelsBehaviorService.deleteChannel(userId,channelId);
    }

}
