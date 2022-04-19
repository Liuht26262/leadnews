package com.tanran.admin.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/17 9:45
 */

@Configuration
@ComponentScan("com.tanran.common.mysql.core")
@MapperScan("com.tanran.admin.dao")
public class MysqlConfig {
}
