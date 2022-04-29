package com.tanran.behavior.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.tanran.behavior.service.LikeBehaviorService;
import com.tanran.common.result.RespResult;
import com.tanran.model.behavior.dtos.LikesBehaviorDto;
import com.tanran.model.behavior.pojos.ApBehaviorEntry;
import com.tanran.model.behavior.pojos.ApLikesBehavior;
import com.tanran.model.common.enums.AppHttpCodeEnum;
import com.tanran.model.mappers.app.BehaviorEntryMapper;
import com.tanran.model.mappers.app.LikeBehaviorMapper;
import com.tanran.model.user.pojos.ApUser;
import com.tanran.utils.threadlocal.AppThreadLocalUtils;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 用户喜欢行为数据存储
 * @since 3.0.x 2022/3/23 19:30
 */
@Service
public class LikeBehaviorServiceImpl implements LikeBehaviorService {
    @Autowired
    private BehaviorEntryMapper behaviorEntryMapper;
    @Autowired
    private LikeBehaviorMapper likeBehaviorMapper;

    @Override
    public RespResult saveLikeBehavior(LikesBehaviorDto dto) {
        ApUser user = AppThreadLocalUtils.getUser();
        if(ObjectUtils.isEmpty(user)&&dto.getEntryId()==null){
            return RespResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        Long id = null;
        if(!ObjectUtils.isEmpty(user)){
            id = user.getId();
        }

        ApBehaviorEntry apBehaviorEntry = behaviorEntryMapper.selectByUserIdOrEquipment(id, dto.getEquipmentId());
        if(ObjectUtils.isEmpty(apBehaviorEntry)){
            RespResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }

        ApLikesBehavior apLikesBehavior = new ApLikesBehavior();
        apLikesBehavior.setBehaviorEntryId(apBehaviorEntry.getEntryId());
        apLikesBehavior.setEntryId(dto.getEntryId());
        apLikesBehavior.setCreatedTime(new Date());
        apLikesBehavior.setType(dto.getType());

        int result = likeBehaviorMapper.saveLikeBehavior(apLikesBehavior);

        return RespResult.okResult(result);
    }
}
