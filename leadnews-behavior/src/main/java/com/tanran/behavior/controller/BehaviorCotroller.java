package com.tanran.behavior.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanran.article.behaviorApi.BehaviorControllerApi;
import com.tanran.behavior.service.BehaviorService;
import com.tanran.common.result.RespResult;
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


    @Override
    @PostMapping("/show_behavior")
    public RespResult saveUserBehavior(ShowBehaviorDto dto) {

        return behaviorService.saveUserBehavior(dto);
    }
}
