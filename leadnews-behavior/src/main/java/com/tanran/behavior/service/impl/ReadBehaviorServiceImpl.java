package com.tanran.behavior.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.tanran.behavior.service.ReadBehaviorService;
import com.tanran.common.result.RespResult;
import com.tanran.common.zookeeper.sequence.Sequences;
import com.tanran.model.behavior.dtos.ReadBehaviorDto;
import com.tanran.model.behavior.pojos.ApBehaviorEntry;
import com.tanran.model.behavior.pojos.ApReadBehavior;
import com.tanran.model.common.enums.ErrorCodeEnum;
import com.tanran.model.mappers.app.BehaviorEntryMapper;
import com.tanran.model.mappers.app.ReadBehaviorMapper;
import com.tanran.model.user.pojos.ApUser;
import com.tanran.utils.threadlocal.AppThreadLocalUtils;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 文章详情阅读行为数据储存shixianlei
 * @since 3.0.x 2022/3/23 20:17
 */
@Service
public class ReadBehaviorServiceImpl implements ReadBehaviorService {
    @Autowired
    private BehaviorEntryMapper behaviorEntryMapper;
    @Autowired
    private Sequences sequences;
    @Autowired
    private ReadBehaviorMapper readBehaviorMapper;

    @Override
    public RespResult saveReadBehavior(ReadBehaviorDto dto) {
        ApUser user = AppThreadLocalUtils.getUser();
        if(ObjectUtils.isEmpty(user)&&dto.getEquipmentId()==null){
            RespResult.errorResult(ErrorCodeEnum.PARAM_REQUIRE);
        }

        Long id = null;
        if(!ObjectUtils.isEmpty(user)){
            id = user.getId();
        }

        ApBehaviorEntry apBehaviorEntry = behaviorEntryMapper.selectByUserIdOrEquipment(id, dto.getEquipmentId());
        if(ObjectUtils.isEmpty(apBehaviorEntry)){
            return RespResult.errorResult(ErrorCodeEnum.PARAM_REQUIRE);
        }

        ApReadBehavior apReadBehavior = readBehaviorMapper.selectReadBehaviorById(apBehaviorEntry.getEntryId(),
            dto.getArticleId());

        boolean isInsert = false;
        if (ObjectUtils.isEmpty(apBehaviorEntry)){
            apReadBehavior = new ApReadBehavior();
            apReadBehavior.setArticleId(dto.getArticleId());
            isInsert = true;
        }
        apReadBehavior.setCount(dto.getCount());
        apReadBehavior.setCreatedTime(new Date());
        apReadBehavior.setEntryId(apBehaviorEntry.getEntryId());
        apReadBehavior.setId(sequences.sequenceApReadBehavior());
        apReadBehavior.setCreatedTime(new Date());
        apReadBehavior.setUpdatedTime(new Date());
        if(isInsert){
            return RespResult.okResult(readBehaviorMapper.saveReadBehavior(apReadBehavior));
        }else{
            return RespResult.okResult(readBehaviorMapper.updateReadBehavior(apReadBehavior));
        }
    }
}
