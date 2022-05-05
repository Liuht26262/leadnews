package com.tanran.common.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import lombok.Getter;
import lombok.Setter;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/5/4 19:06
 */
@Setter
@Getter
@Configuration
@PropertySource("classpath:mail.properties")
public class mailConfig {
    @Autowired
    private JavaMailSender javaMailSender;

    private String from = "1606566492@qq.com";


    public void contextLoads() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setSubject("主题：简单邮件");
        message.setText("测试邮件内容");
        javaMailSender.send(message);
    }

}
