package com.tanran.admin.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.tanran.common.mysql.core")
@MapperScan("com.tanran.admin.dao")
public class MysqlConfig {
}
