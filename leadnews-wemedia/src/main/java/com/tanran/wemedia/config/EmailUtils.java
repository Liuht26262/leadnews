package com.tanran.wemedia.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.tanran.model.email.EmailSendDto;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/5/4 19:06
 */
@Component
@PropertySource("classpath:mail.properties")
@Slf4j
public class EmailUtils {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private  String from;


    public  void contextLoads(EmailSendDto dto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        if(dto.getTos().size()>1){
            for(String to : dto.getTos()){
                message.setTo(to);
                message.setSubject(dto.getSubject());
                message.setText(dto.getContent());
                javaMailSender.send(message);
                log.info("发送给"+to+"的邮件推送成功");
            }
        }else {
            message.setTo(dto.getTos().get(0));
            message.setSubject(dto.getSubject());
            message.setText(dto.getContent());
            System.out.println("++++++++++++++++++++++++++++++"+javaMailSender);
            javaMailSender.send(message);
            log.info("发送给"+dto.getTos().get(0)+"的邮件推送成功");
        }
    }


    }
