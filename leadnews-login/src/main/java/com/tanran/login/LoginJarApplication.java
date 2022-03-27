package com.tanran.login;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/3/24 21:27
 */
@SpringBootApplication
@MapperScan(basePackages = "com.tanran.model.mappers.app")
public class LoginJarApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoginJarApplication.class,args);
    }
}
