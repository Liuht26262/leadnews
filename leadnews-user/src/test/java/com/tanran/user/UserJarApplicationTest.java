package com.tanran.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ObjectUtils;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 3.0.x 2022/3/23 14:15
 */
@SpringBootTest(classes=UserJarApplication.class)
@RunWith(SpringRunner.class)
public class UserJarApplicationTest {

    @Test
    public void test(){
        System.out.println(ObjectUtils.isEmpty(null));
    }

}
