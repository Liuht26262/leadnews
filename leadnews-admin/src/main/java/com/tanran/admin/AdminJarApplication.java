package com.tanran.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.tanran.model.mappers.admin")
public class AdminJarApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminJarApplication.class,args);
    }
}
