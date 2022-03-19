package com.tanran.model.mappers.app;

import com.tanran.model.behavior.pojos.ApBehaviorEntry;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 3.0.x 2022/3/19 10:40
 */
public interface BehaviorEntryMapper {

    ApBehaviorEntry selectByUserIdOrEquipment(Long userId, Integer equipmentId);
}
