package com.tanran.wemedia.config;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ServletComponentScan("com.tanran.common.web.wm.security")
public class SecurityConfig {
}
