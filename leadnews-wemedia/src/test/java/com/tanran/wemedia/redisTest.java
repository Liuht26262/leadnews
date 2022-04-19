package com.tanran.wemedia;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.tanran.model.media.pojos.WmMaterial;

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
    private RedisTemplate redisTemplate;

    @Test
    public void contextLoads() {
        WmMaterial material = WmMaterial.builder()
            .build();

        material.setUrl("OOOOOOO");

        redisTemplate.opsForValue().set("material",material);
    }

}
