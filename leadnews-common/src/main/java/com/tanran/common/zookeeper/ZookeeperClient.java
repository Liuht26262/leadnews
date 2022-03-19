package com.tanran.common.zookeeper;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.shaded.com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tanran.common.zookeeper.sequence.ZkSequence;
import com.tanran.common.zookeeper.sequence.ZkSequenceEnum;

import lombok.Getter;
import lombok.Setter;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 链接zookeeper客户端
 * @since 3.0.x 2022/3/19 15:59
 */
@Setter
@Getter
public class ZookeeperClient  {
    private static Logger logger = LoggerFactory.getLogger(ZookeeperClient.class);


    private String host;
    private String sequencePath;

    // 重试休眠时间
    private final int SLEEP_TIME_MS = 1000;
    // 最大重试1000次
    private final int MAX_RETRIES = 1000;
    //会话超时时间
    private final int SESSION_TIMEOUT = 30 * 1000;
    //连接超时时间
    private final int CONNECTION_TIMEOUT = 3 * 1000;

    //创建连接实例
    private CuratorFramework client = null;
    // 序列化集合，key:表名  value:逐渐序列
    private Map<String, ZkSequence> zkSequenceMap = Maps.newConcurrentMap();

    public ZookeeperClient(String host,String sequencePath){
        this.host = host;
        this.sequencePath = sequencePath;
    }

    /***
     * 初始化客户端,PostConstruct表示在初始化这个类的时候会执行这个方法
     */
    @PostConstruct
    public void init() {
        this.client = CuratorFrameworkFactory.builder().connectString(this.getHost())
            /*设置连接参数*/
            .connectionTimeoutMs(CONNECTION_TIMEOUT)
            .sessionTimeoutMs(SESSION_TIMEOUT)
            .retryPolicy(new ExponentialBackoffRetry(SLEEP_TIME_MS, MAX_RETRIES)).build();
        this.client.start();
        this.initZkSequence();
    }

    private String getHost() {
        return this.host;
    }

    public void initZkSequence() {
        ZkSequenceEnum[] list = ZkSequenceEnum.values();
        /*根据表名获取到这个表的序列*/
        for (int i = 0; i < list.length; i++) {
            String name = list[i].name();
            String path = this.sequencePath + name;
            ZkSequence zkSequence = new ZkSequence(path,this.client);
            zkSequenceMap.put(name, zkSequence);
        }
    }

    /**
     * 生成SEQ
     * @param tableName
     * @return Long
     * @throws Exception
     */
    public Long sequence(ZkSequenceEnum tableName) {
        try {
            ZkSequence seq = zkSequenceMap.get(tableName.name());
            if (seq != null) {
                return seq.sequence();
            }
        } catch (Exception e) {
            logger.error("获取[{}]Sequence错误:{}",tableName,e);
            e.printStackTrace();
        }
        return null;

    }
}