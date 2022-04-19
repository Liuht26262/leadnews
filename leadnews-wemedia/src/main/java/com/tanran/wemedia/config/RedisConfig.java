package com.tanran.wemedia.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/16 17:23
 */
@EnableCaching
@Configuration
@ComponentScan("com.tanran.common.redis")
public class RedisConfig {
}
