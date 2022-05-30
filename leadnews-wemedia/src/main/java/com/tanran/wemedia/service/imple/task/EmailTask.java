package com.tanran.wemedia.service.imple.task;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.tanran.common.result.RespResult;
import com.tanran.model.article.pojos.ApArticle;
import com.tanran.model.email.EmailSendDto;
import com.tanran.model.mappers.app.ApUserChannelMapper;
import com.tanran.model.mappers.app.ApUserFollowMapper;
import com.tanran.model.mappers.app.ApUserMapper;
import com.tanran.model.mappers.wemedia.SendLogMapper;
import com.tanran.model.media.pojos.SendLog;
import com.tanran.model.user.pojos.ApUser;
import com.tanran.model.user.pojos.ApUserChannel;
import com.tanran.model.user.pojos.ApUserFollow;
import com.tanran.wemedia.config.EmailUtils;

import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/5/19 16:34
 */
@Component
@Slf4j
public class EmailTask {
    @Autowired
    private ApUserMapper apUserMapper;
    @Autowired
    private ApUserChannelMapper apUserChannelMapper;
    @Autowired
    private ApUserFollowMapper apUserFollowMapper;
    @Autowired
    private EmailUtils emailUtils;
    @Autowired
    private SendLogMapper sendLogMapper;
    /**
     * 推送邮件
     * @param article
     */
    @Async
    public void sendMaile(ApArticle article){
        log.info("当前线程是"+Thread.currentThread().getName());
        List<ApUserFollow> apUserFollows = apUserFollowMapper.selectUserFollowByAuthorId(article.getAuthorId());
        List<ApUserChannel> apUserChannels = apUserChannelMapper.selectByChannelId(article.getChannelId());
        List<Long> fansIds = null;
        List<Long> subscriptionIds;
        List<String> fansEmails;
        if(!Collections.isEmpty(apUserFollows)){
            fansIds= apUserFollows.stream()
                .map(fans -> fans.getUserId())
                .collect(Collectors.toList());
            List<ApUser> apUsers = apUserMapper.selectApUserByIds(fansIds, 100, 0);
            fansEmails = apUsers.stream()
                .map(user -> user.getMail())
                .collect(Collectors.toList());
            EmailSendDto fansMailDto = new EmailSendDto();
            fansMailDto.setTos(fansEmails);
            fansMailDto.setSubject("文章推送");
            fansMailDto.setContentByFans(article.getAuthorName(), article.getTitle());
            RespResult respResult = emailUtils.contextLoads(fansMailDto);
            if(respResult.getCode()!=0){
                retrySend(0,fansMailDto);
            }
        }
        if(!Collections.isEmpty(apUserChannels)) {
            subscriptionIds = apUserChannels.stream()
                .map(userChannel -> userChannel.getUserId()
                    .longValue())
                .collect(Collectors.toList());
            //去重
            if(!Collections.isEmpty(fansIds)){
                for(Long userId : fansIds){
                    if(subscriptionIds.contains(userId)){
                        subscriptionIds.remove(userId);
                    }
                }
            }
            if (!Collections.isEmpty(subscriptionIds)) {
                List<ApUser> subscriptionUsers = apUserMapper.selectApUserByIds(subscriptionIds, 100, 0);
                List<String> subscriptionUsersEmails = subscriptionUsers.stream()
                    .map(user -> user.getMail())
                    .collect(Collectors.toList());
                EmailSendDto subscriptionMailDto = new EmailSendDto();
                subscriptionMailDto.setTos(subscriptionUsersEmails);
                subscriptionMailDto.setSubject("频道订阅文章推送");
                subscriptionMailDto.setContentBySubscription(article.getChannelName(), article.getTitle());
                RespResult respResult = emailUtils.contextLoads(subscriptionMailDto);
                if(respResult.getCode()!=0){
                    retrySend(0,subscriptionMailDto);
                }
            }
        }

    }

    private void retrySend(int sendCount,EmailSendDto dto) {
        sendCount++;
        log.info("==============开始第{}次重试============="+sendCount);
        RespResult respResult = emailUtils.contextLoads(dto);
        if(respResult.getCode() == 3){
            log.error("===================重试失败，将数据存入邮件日志表================");
            SendLog sendLog = new SendLog();
            sendLog.setSendMessage(dto.toString());
            sendLog.setCreatedTime(new Date(System.currentTimeMillis()));
            sendLogMapper.insertSelective(sendLog);
        }else {
            retrySend(sendCount,dto);
        }
    }
}
