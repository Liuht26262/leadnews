package com.tanran.behavior.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanran.api.behaviorApi.BehaviorControllerApi;
import com.tanran.behavior.service.BehaviorService;
import com.tanran.behavior.service.LikeBehaviorService;
import com.tanran.behavior.service.ReadBehaviorService;
import com.tanran.common.result.RespResult;
import com.tanran.model.behavior.dtos.LikesBehaviorDto;
import com.tanran.model.behavior.dtos.ReadBehaviorDto;
import com.tanran.model.behavior.dtos.ShowBehaviorDto;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 3.0.x 2022/3/18 18:18
 */
@RestController
@RequestMapping("/api/v1/behavior")
public class BehaviorCotroller implements BehaviorControllerApi {
    @Autowired
    private BehaviorService behaviorService;
    @Autowired
    private LikeBehaviorService likeBehaviorService;
    @Autowired
    private ReadBehaviorService readBehaviorService;


    @Override
    @PostMapping("/show_behavior")
    public RespResult saveUserBehavior(@RequestBody ShowBehaviorDto dto) {
        return behaviorService.saveUserBehavior(dto);
    }

    /**存储用户喜欢的行为数据*/
    @Override
    @PostMapping("/like_behavior")
    public RespResult saveLikeBehavior(@RequestBody LikesBehaviorDto dto) {
        return likeBehaviorService.saveLikeBehavior(dto);
    }

    /**存储用户阅读文章详情的行为数据*/
    @Override
    @PostMapping("/read_behavior")
    public RespResult saveReadBehavior(@RequestBody ReadBehaviorDto dto) {
        return readBehaviorService.saveReadBehavior(dto);
    }
}
