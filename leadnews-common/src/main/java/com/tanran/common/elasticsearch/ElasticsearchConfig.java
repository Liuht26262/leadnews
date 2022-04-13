package com.tanran.common.elasticsearch;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import lombok.Data;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/3/31 14:01
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.elasticsearch.jest")
@PropertySource("classpath:elasticserach.properties")
public class ElasticsearchConfig {
    private String url;
    private Integer readTimeout;
    private Integer connectionTimeout;

    @Bean
    public JestClient getJestCilent(){
        JestClientFactory jestClientFactory = new JestClientFactory();
        jestClientFactory.setHttpClientConfig(new HttpClientConfig.Builder(this.url)
            .multiThreaded(true)
            .readTimeout(this.readTimeout)
            .connTimeout(this.connectionTimeout).build());

        return jestClientFactory.getObject();
    }
}
