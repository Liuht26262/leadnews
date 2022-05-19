package com.tanran.wemedia;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/12 11:37
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.tanran.model.mappers.wemedia"})
@EnableScheduling
@EnableAsync
public class WemediaJarApplication {
    public static void main(String[] args) {
        SpringApplication.run(WemediaJarApplication.class,args);
    }
}
