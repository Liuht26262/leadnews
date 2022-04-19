package com.tanran.admin.config;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/17 9:50
 */

@Configuration
@ServletComponentScan("com.tanran.common.web.admin.security")
public class SecurityConfig {
}
