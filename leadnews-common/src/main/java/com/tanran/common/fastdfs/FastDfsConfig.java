package com.tanran.common.fastdfs;

import java.util.Arrays;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.luhuiguo.fastdfs.FdfsAutoConfiguration;
import com.luhuiguo.fastdfs.FdfsProperties;
import com.luhuiguo.fastdfs.conn.FdfsConnectionPool;
import com.luhuiguo.fastdfs.conn.PooledConnectionFactory;
import com.luhuiguo.fastdfs.conn.TrackerConnectionManager;

import lombok.Getter;
import lombok.Setter;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 图片上传的配置信息
 * @since 2022/4/14 14:52
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix="fast.dfs")
@PropertySource("classpath:fast-dfs.properties")
public class FastDfsConfig extends FdfsAutoConfiguration {

    int soTimeout;
    int connectTimeout;
    String trackerServer;

    public FastDfsConfig(FdfsProperties properties){
        super(properties);
    }

    @Override
    @Bean
    public PooledConnectionFactory pooledConnectionFactory() {
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory();
        pooledConnectionFactory.setSoTimeout(getSoTimeout());
        pooledConnectionFactory.setConnectTimeout(getConnectTimeout());
        return pooledConnectionFactory;
    }


    @Override
    @Bean
    public TrackerConnectionManager trackerConnectionManager(FdfsConnectionPool fdfsConnectionPool) {
        return new TrackerConnectionManager(fdfsConnectionPool, Arrays.asList(trackerServer));
    }
}
