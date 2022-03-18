package com.tanran.behavior.service.impl;


import org.springframework.util.ObjectUtils;

import com.tanran.behavior.service.BehaviorService;
import com.tanran.common.result.RespResult;
import com.tanran.model.behavior.dtos.ShowBehaviorDto;
import com.tanran.model.common.enums.AppHttpCodeEnum;
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
public class BehviorServiceImpl implements BehaviorService {
    @Override
    public RespResult saveUserHavior(ShowBehaviorDto dto) {
        ApUser user = AppThreadLocalUtils.getUser();
        /**设备和用户不能同时为空*/
        if(ObjectUtils.isEmpty(user)&&(dto.getArticleIds()==null||dto.getArticleIds().isEmpty())){
            return RespResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        Long id = null;
        if(user != null){
             id = user.getId();
        }



        return null;
    }
}
