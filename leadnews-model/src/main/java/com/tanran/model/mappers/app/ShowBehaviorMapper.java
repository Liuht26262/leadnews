package com.tanran.model.mappers.app;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tanran.model.behavior.pojos.ApShowBehavior;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 3.0.x 2022/3/18 19:13
 */
public interface ShowBehaviorMapper {
    List<ApShowBehavior> slelectByEntryAndArticleIds(@Param("entryId")Integer entryId,@Param("articleIds")Integer[] articleIds);

    void saveBehaviors(Integer entryId, Integer[] articleIds);
}
