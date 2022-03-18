package com.tanran.controller.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tanran.common.constans.ArticleConstans;
import com.tanran.common.result.RespResult;
import com.tanran.controller.ArticleJarApplication;
import com.tanran.controller.service.ArticleHomeService;
import com.tanran.model.article.dtos.ArticleHomeDto;
import com.tanran.utils.threadlocal.AppThreadLocalUtils;
import com.tanran.model.user.pojos.ApUser;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 文章测试类
 * @since 3.0.x 2022/3/18 13:37
 */

@SpringBootTest(classes = ArticleJarApplication.class)
@RunWith(SpringRunner.class)
public class ArticleJarApplicationTest {

    @Autowired
    private ArticleHomeService articleService;

    @Test
    public void testArticle(){
        ApUser apUser = new ApUser();
        apUser.setId(2104l);
        AppThreadLocalUtils.setUser(apUser);


        RespResult result = articleService.load(null, ArticleConstans.LOADTYPE_LOAD_MORE);
        System.out.println(result.getData());
    }
}
