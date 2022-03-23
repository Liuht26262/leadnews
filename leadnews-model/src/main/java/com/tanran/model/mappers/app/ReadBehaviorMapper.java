package com.tanran.model.mappers.app;

import com.tanran.model.behavior.pojos.ApReadBehavior;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 3.0.x 2022/3/23 20:25
 */
public interface ReadBehaviorMapper {
     int saveReadBehavior(ApReadBehavior apReadBehavior);
     ApReadBehavior selectReadBehaviorById(Integer entryId,Integer articleId);
     int updateReadBehavior(ApReadBehavior apReadBehavior);
}
