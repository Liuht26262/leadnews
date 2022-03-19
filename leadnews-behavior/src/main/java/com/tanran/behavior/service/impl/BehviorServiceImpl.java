package com.tanran.behavior.service.impl;


import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.tanran.behavior.service.BehaviorService;
import com.tanran.common.result.RespResult;
import com.tanran.model.behavior.dtos.ShowBehaviorDto;
import com.tanran.model.behavior.pojos.ApBehaviorEntry;
import com.tanran.model.behavior.pojos.ApShowBehavior;
import com.tanran.model.common.enums.AppHttpCodeEnum;
import com.tanran.model.mappers.app.BehaviorEntryMapper;
import com.tanran.model.mappers.app.ShowBehaviorMapper;
import com.tanran.model.user.pojos.ApUser;
import com.tanran.utils.threadlocal.AppThreadLocalUtils;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 存储用户数据业务层实现类
 * @since 3.0.x 2022/3/18 18:35
 */
@Service
public class BehviorServiceImpl implements BehaviorService {
    @Autowired
    private ShowBehaviorMapper showBehaviorMapper;

    @Autowired
    private BehaviorEntryMapper behaviorEntryMapper;
    /**
     * 1、获取用户信息，获取设备id
     * 2、根据设备id或用户信息查询行为实体
     * 3、过去传递过来的文章id列表
     * 4、根据文章的id列表和设备id查询文章展现行为表
     * 5、传递过来的文章id如果在表中已经存在，就删除
     * 6、其他的数据存入表中
     *
     */
    @Override
    public RespResult saveUserBehavior(ShowBehaviorDto dto) {


        ApUser user = AppThreadLocalUtils.getUser();
        /**设备和用户不能同时为空*/
        if(ObjectUtils.isEmpty(user)&&(dto.getArticleIds()==null||dto.getArticleIds().isEmpty())){
            return RespResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        Long userId = null;
        if(user != null){
             userId = user.getId();
        }

        ApBehaviorEntry apBehaviorEntry = behaviorEntryMapper.selectByUserIdOrEquipment(userId,dto.getEquipmentId());
        if (ObjectUtils.isEmpty(apBehaviorEntry)) {
            return RespResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }

        //获取文章id列表
        Integer[] articleIds = new Integer[dto.getArticleIds().size()];
        for (int i = 0; i<articleIds.length;i++){
            articleIds[i] = dto.getArticleIds().get(i).getId();
        }

        //查询数据库中的文章展现行为表，进行对比过滤
        List<ApShowBehavior> apShowBehaviors = showBehaviorMapper.slelectByEntryAndArticleIds(
            apBehaviorEntry.getEntryId(), articleIds);
        List<Integer> articleIdList = Arrays.asList(articleIds);
        if (!apShowBehaviors.isEmpty()){
            apShowBehaviors.forEach(item ->{
                    articleIdList.remove(item.getArticleId());
                }
            );
        }

        //存储数据
       if(articleIdList.isEmpty()){
           Integer[] temp = new Integer[articleIdList.size()];
           articleIdList.toArray(temp);
           showBehaviorMapper.saveBehaviors(apBehaviorEntry.getId(),temp);
       }

        return null;
    }
}
