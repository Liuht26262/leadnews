package com.tanran.behavior;

import javax.sound.midi.Sequence;

import org.junit.Test;
import org.junit.internal.Classes;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tanran.common.zookeeper.ZookeeperClient;
import com.tanran.common.zookeeper.sequence.Sequences;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description Zookeeper连接测试类
 * @since 3.0.x 2022/3/19 17:22
 */
@SpringBootTest(classes=BehaviorJarApplication.class)
@RunWith(SpringRunner.class)
public class ZookeeperConnTest {

    @Autowired
    private Sequences sequences;

    @Test
    public void getTableSequence(){
        Long aLong = sequences.sequenceApReadBehavior();
        System.out.println("******************************************");
        System.out.println(aLong);
        System.out.println("******************************************");
    }
}
