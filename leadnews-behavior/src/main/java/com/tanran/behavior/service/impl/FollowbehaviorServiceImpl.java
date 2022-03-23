package com.tanran.behavior.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.tanran.behavior.service.FollowBehaviorService;
import com.tanran.common.result.RespResult;
import com.tanran.model.behavior.dtos.FollowBehaviorDto;
import com.tanran.model.behavior.pojos.ApBehaviorEntry;
import com.tanran.model.behavior.pojos.ApFollowBehavior;
import com.tanran.model.common.enums.AppHttpCodeEnum;
import com.tanran.model.mappers.app.BehaviorEntryMapper;
import com.tanran.model.mappers.app.FollowBehaviorMapper;
import com.tanran.model.user.pojos.ApUser;
import com.tanran.utils.threadlocal.AppThreadLocalUtils;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 保存用户关注行为
 * @since 3.0.x 2022/3/23 15:15
 */
@Service
public class FollowbehaviorServiceImpl implements FollowBehaviorService {
    @Autowired
    private FollowBehaviorMapper followBehaviorMapper;
    @Autowired
    private BehaviorEntryMapper behaviorEntryMapper;

    @Override
    @Async
    public RespResult save(FollowBehaviorDto dto) {
        ApUser user = AppThreadLocalUtils.getUser();
        if(user==null &&dto.getEquipmentId()==null){
            return RespResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        Long userId = null;
        if(user!=null){
            userId = user.getId();
        }
        ApBehaviorEntry apBehaviorEntry = behaviorEntryMapper.selectByUserIdOrEquipment(userId, dto.getEquipmentId());
        if(apBehaviorEntry==null){
            return RespResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //保存行为
        ApFollowBehavior alb = new ApFollowBehavior();
        alb.setEntryId(apBehaviorEntry.getId());
        alb.setArticleId(dto.getArticleId());
        alb.setFollowId(dto.getFollowId());
        alb.setCreatedTime(new Date());
        int insert = followBehaviorMapper.save(alb);
        return RespResult.okResult(insert);
    }
}
