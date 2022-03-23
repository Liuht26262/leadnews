package com.tanran.behavior;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 3.0.x 2022/3/18 18:25
 */
@SpringBootApplication
@MapperScan("com.tanran.model.mappers.app")
public class BehaviorJarApplication {
    public static void main(String[] args) {
        SpringApplication.run(BehaviorJarApplication.class,args);
    }
}
