package com.tanran.behavior.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanran.api.behaviorApi.UnLikeBehaviorControllerApi;
import com.tanran.behavior.service.UnLikeBehaviorService;
import com.tanran.common.result.RespResult;
import com.tanran.model.behavior.dtos.UnLikesBehaviorDto;

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
public class UnLikeBehaviorController implements UnLikeBehaviorControllerApi {

    @Autowired
    private UnLikeBehaviorService unLikeBehaviorService;

    @PostMapping("/unlike_behavior")
    @Override
    public RespResult SaveUnLikeBehavior(@RequestBody UnLikesBehaviorDto dto) {
        return unLikeBehaviorService.SaveUnLikeBehavior(dto);
    }
}
