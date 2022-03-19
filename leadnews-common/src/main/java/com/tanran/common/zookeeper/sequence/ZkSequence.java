package com.tanran.common.zookeeper.sequence;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 用于封装程序在运行时每个表对应的自增器，这里是通过原子自增类（DistributedAtomicLong）实现
 *              设定每500毫秒重试3次后仍然失败就返回null
 * @since 3.0.x 2022/3/19 15:54
 */
public class ZkSequence {

    RetryPolicy retryPolicy = new ExponentialBackoffRetry(500, 3);

    DistributedAtomicLong distAtomicLong;

    public ZkSequence(String sequenceName, CuratorFramework client){
        distAtomicLong = new DistributedAtomicLong(client,sequenceName,retryPolicy);
    }

    /**
     * 生成序列
     * @return
     * @throws Exception
     */
    public Long sequence() throws Exception{
        /*主键自增*/
        AtomicValue<Long> sequence = this.distAtomicLong.increment();
        if(sequence.succeeded()){
            return sequence.postValue();
        }else{
            return null;
        }
    }
}
