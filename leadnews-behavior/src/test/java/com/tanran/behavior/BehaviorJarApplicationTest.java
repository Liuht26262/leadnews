package com.tanran.behavior;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tanran.behavior.service.BehaviorService;
import com.tanran.common.result.RespResult;
import com.tanran.model.article.pojos.ApArticle;
import com.tanran.model.behavior.dtos.ShowBehaviorDto;
import com.tanran.model.user.pojos.ApUser;
import com.tanran.utils.threadlocal.AppThreadLocalUtils;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 3.0.x 2022/3/19 11:45
 */
@SpringBootTest(classes = BehaviorJarApplication.class)
@RunWith(SpringRunner.class)
public class BehaviorJarApplicationTest {

    @Autowired
    private BehaviorService behaviorService;

    @Test
    public void BehaviorTest(){
        ApUser user = new ApUser();
        user.setId(1l);
        AppThreadLocalUtils.setUser(user);
        ShowBehaviorDto dto = new ShowBehaviorDto();
        List<ApArticle> list = new ArrayList<>();
        ApArticle apArticle = new ApArticle();
        apArticle.setId(200);
        list.add(apArticle);
        dto.setArticleIds(list);
        RespResult respResult = behaviorService.saveUserBehavior(dto);
        System.out.println(respResult.getMsg());
    }
}
