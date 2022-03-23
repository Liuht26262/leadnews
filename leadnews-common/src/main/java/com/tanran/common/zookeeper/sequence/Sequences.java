package com.tanran.common.zookeeper.sequence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tanran.common.zookeeper.ZookeeperClient;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description Sequences封装类，方便获得某个表的主键序列
 * @since 3.0.x 2022/3/19 16:21
 */
@Component
public class Sequences {

    @Autowired
    private ZookeeperClient client;

    public Long sequenceApLikes(){
        return this.client.sequence(ZkSequenceEnum.AP_LIKES);
    }

    public Long sequenceApReadBehavior(){
        return this.client.sequence(ZkSequenceEnum.AP_READ_BEHAVIOR);
    }

    public Long sequenceApCollection(){
        return this.client.sequence(ZkSequenceEnum.AP_COLLECTION);
    }

    public Long sequenceApUserFollow(){return this.client.sequence(ZkSequenceEnum.AP_USER_FOLLOW);}

    public Long sequenceApUserFan(){return this.client.sequence(ZkSequenceEnum.AP_USER_FAN);}

}