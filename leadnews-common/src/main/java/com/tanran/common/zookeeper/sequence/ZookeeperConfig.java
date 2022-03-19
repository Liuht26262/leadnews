package com.tanran.common.zookeeper.sequence;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.tanran.common.zookeeper.ZookeeperClient;

import lombok.Getter;
import lombok.Setter;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description zookeeper配置类
 * @since 3.0.x 2022/3/19 16:18
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix="zk")
@PropertySource("classpath:zookeeper.properties")
public class ZookeeperConfig {

    String host;
    String sequencePath;


    @Bean
    public ZookeeperClient zookeeperClient(){
        return new ZookeeperClient(this.host,this.sequencePath);
    }

}