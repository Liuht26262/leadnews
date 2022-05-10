package com.tanran.wemedia.SchedulingTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tanran.model.mappers.wemedia.WmNewsMapper;
import com.tanran.model.media.pojos.WmNews;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description  文章定时
 * @since 2022/5/9 15:36
 */
@Component
public class ArticleTask {
    @Autowired
    private WmNewsMapper wmNewsMapper;

    private WmNews wmNews;

    @Scheduled(cron = "*/1 * * * * ?")
    public void synchronizeArticle(){

    }
}
