package com.tanran.behavior.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.tanran.behavior.service.UnLikeBehaviorService;
import com.tanran.common.result.RespResult;
import com.tanran.model.behavior.dtos.ShowBehaviorDto;
import com.tanran.model.behavior.dtos.UnLikesBehaviorDto;
import com.tanran.model.behavior.pojos.ApBehaviorEntry;
import com.tanran.model.behavior.pojos.ApUnlikesBehavior;
import com.tanran.model.common.enums.AppHttpCodeEnum;
import com.tanran.model.mappers.app.BehaviorEntryMapper;
import com.tanran.model.mappers.app.UnLikeBehaviorMapper;
import com.tanran.model.user.pojos.ApUser;
import com.tanran.utils.threadlocal.AppThreadLocalUtils;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 不喜欢行为实现类
 * @since 3.0.x 2022/3/23 17:38
 */
@Service
public class UnLikeBehaviorServiceImpl implements UnLikeBehaviorService {
    @Autowired
    private UnLikeBehaviorMapper unLikeBehaviorMapper;
    @Autowired
    private BehaviorEntryMapper behaviorEntryMapper;

    @Override
    public RespResult SaveUnLikeBehavior(UnLikesBehaviorDto dto) {
        ApUser user = AppThreadLocalUtils.getUser();
        if(ObjectUtils.isEmpty(user)&&dto.getEquipmentId()==null){
            return RespResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE,"缺少参数");
        }
        Long id = null;
        if(!ObjectUtils.isEmpty(user)){
            id = user.getId();
        }
        ApBehaviorEntry apBehaviorEntry = behaviorEntryMapper.selectByUserIdOrEquipment(id, dto.getEquipmentId());
        if(apBehaviorEntry==null){
            RespResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }

        //存入数据
        ApUnlikesBehavior apUnlikesBehavior = new ApUnlikesBehavior();
        apUnlikesBehavior.setArticleId(dto.getArticleId());
        apUnlikesBehavior.setEntryId(dto.getEquipmentId());
        apUnlikesBehavior.setCreatedTime(new Date());
        apUnlikesBehavior.setType(dto.getType());

        int result = unLikeBehaviorMapper.SaveUnLikeBeHaviorMapper(apUnlikesBehavior);

        return RespResult.okResult(result);
    }

    /**取消不喜欢*/
    @Override
    public RespResult deleteUsweBehavior(UnLikesBehaviorDto dto) {
        ApUser user = AppThreadLocalUtils.getUser();
        if(ObjectUtils.isEmpty(user)&&dto.getEquipmentId()==null){
            return RespResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE,"缺少参数");
        }
        Long id = null;
        if(!ObjectUtils.isEmpty(user)){
            id = user.getId();
        }
        ApBehaviorEntry apBehaviorEntry = behaviorEntryMapper.selectByUserIdOrEquipment(id, dto.getEquipmentId());
        if(apBehaviorEntry==null){
            RespResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }

        ApUnlikesBehavior apUnlikesBehavior = unLikeBehaviorMapper.selectUnLikeByEntryId(apBehaviorEntry.getEntryId(),
            dto.getArticleId());

        if (!ObjectUtils.isEmpty(apUnlikesBehavior)){
            int result = unLikeBehaviorMapper.deleteUnLikeBehaviorMapper(apBehaviorEntry.getEntryId(), dto.getArticleId());
            return RespResult.okResult(result);
        }else {
            return RespResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }


    }
}
