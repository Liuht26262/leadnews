package com.tanran.login;

import java.util.Objects;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/3/24 21:30
 */
@SpringBootTest(classes = LoginJarApplication.class)
@RunWith(SpringRunner.class)
public class LoginJarApplicationTest {

    @Test
    public void test(){
        System.out.println(Objects.isNull(null));
    }
}
