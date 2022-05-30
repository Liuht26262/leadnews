package com.tanran.wemedia.SchedulingTask;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanran.model.email.EmailSendDto;
import com.tanran.model.mappers.wemedia.SendLogMapper;
import com.tanran.model.media.pojos.SendLog;
import com.tanran.wemedia.config.EmailUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 对邮件日志进行重发
 * @since 2022/5/29 9:50
 */
@Component
@Slf4j
public class EmaillogTask {
    @Autowired
    private SendLogMapper sendLogMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private EmailUtils emailUtils;

    @Scheduled(cron = "*/60 * * * * ?")
    public void emailTryTask(){
        List<SendLog> emailList = sendLogMapper.selectAll();
        emailList.stream().forEach(sendLog -> {
            try {
                EmailSendDto dto = objectMapper.readValue(sendLog.getSendMessage(), EmailSendDto.class);
                emailUtils.contextLoads(dto);
                log.info("==================发送完成，即将删除数据===============");
                sendLogMapper.deleteByPrimaryKey(sendLog.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
