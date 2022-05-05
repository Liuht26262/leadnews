package com.tanran.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanran.api.userApi.UserRelationApi;
import com.tanran.common.result.RespResult;
import com.tanran.model.user.dtos.UserRelationDto;
import com.tanran.model.user.pojos.ApUser;
import com.tanran.user.service.UserDetailService;
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
    @Autowired
    private UserDetailService userDetailService;

    @Override
    @PostMapping("/followings")
    public RespResult userFollow(@RequestBody UserRelationDto userRelationDto) {
        return userRelationService.userFollow(userRelationDto);
    }

    @Override
    @PostMapping("/cancelFollowings")
    public RespResult cancelUserFollow(@RequestBody UserRelationDto userRelationDto) {
        return userRelationService.cancelUserFollow(userRelationDto);
    }

    @Override
    @GetMapping("/{user_id}")
    public RespResult selectUserById(@PathVariable("user_id") Long id) {
        return userDetailService.selectUserById(id);
    }

    @Override
    @GetMapping("/channel/{user_id}")
    public RespResult selectChannelByUser(@PathVariable("user_id") Long id) {
        return userDetailService.selectChannelByUser(id);
    }

    @Override
    @PostMapping("/profile")
    public RespResult userProfile(@RequestBody ApUser user) {
        return userDetailService.updateUserProfile(user);
    }
}
