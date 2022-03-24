package com.tanran.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanran.article.userApi.UserRelationApi;
import com.tanran.common.result.RespResult;
import com.tanran.model.user.dtos.UserRelationDto;
import com.tanran.user.service.UserRelationService;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 3.0.x 2022/3/22 19:31
 */
@RestController
@RequestMapping("api/v1/user")
public class UserRelationController implements UserRelationApi {

    @Autowired
    private UserRelationService userRelationService;

    @Override
    @PostMapping("/user_follow")
    public RespResult userFollow(UserRelationDto userRelationDto) {
        return userRelationService.userFollow(userRelationDto);
    }
}
