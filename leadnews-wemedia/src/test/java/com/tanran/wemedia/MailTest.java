package com.tanran.wemedia;

import java.util.ArrayList;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tanran.model.email.EmailSendDto;
import com.tanran.wemedia.config.EmailUtils;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/5/19 9:40
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MailTest {
    @Autowired
    private EmailUtils emailUtils;

    @Test
    public void test(){
        EmailSendDto emailSendDto = new EmailSendDto();
        ArrayList<String> tos = Lists.newArrayList();
        tos.add("2972944461@qq.com");
        emailSendDto.setTos(tos);
        emailSendDto.setSubject("测试信息");
        emailSendDto.setContentByFans("云中鹤","这是一封测试邮件");
        emailUtils.contextLoads(emailSendDto);
    }
}
