package com.tanran.common.redis;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.tanran.model.article.dtos.ArticleRequestDto;
import com.tanran.model.article.pojos.ApArticle;
import com.tanran.model.mappers.app.ArticleMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/5/25 16:52
 */
@Component
@Slf4j
public class RedisTask {
    @Resource
    private RedisUtils redisUtils;
    @Autowired
    private ArticleMapper articleMapper;

    @Async
    public void rennewRedis(Integer channelId){
        String key = "channel:"+channelId;
        /**先清除缓存
         * 再写入缓存
         * */
        redisUtils.deleteObject(key);
        ArticleRequestDto articleRequestDto = new ArticleRequestDto();
        articleRequestDto.setChannelId(channelId);
        List<ApArticle> apArticles = articleMapper.selectArticleByChannelId(articleRequestDto);
        if(Objects.nonNull(apArticles)||apArticles.size()>0){
            redisUtils.setCacheList(key,apArticles);
            redisUtils.expire(key,1, TimeUnit.DAYS);
            log.info("================更新缓存成功====================");
        }

    }

}
