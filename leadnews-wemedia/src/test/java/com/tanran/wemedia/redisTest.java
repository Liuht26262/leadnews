package com.tanran.wemedia;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tanran.common.redis.RedisUtils;
import com.tanran.model.article.pojos.ApArticleContent;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/16 17:14
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class redisTest {

    @Resource
    private RedisUtils redis;

    @Test
    public void contextLoads() {
        ApArticleContent apArticleContent = new ApArticleContent();
        apArticleContent.setContent("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
        apArticleContent.setArticleId(212);
        apArticleContent.setId(1);

        redis.setCacheObject("content",apArticleContent);
        System.out.println((ApArticleContent)redis.getCacheObject("content"));

    }

}
