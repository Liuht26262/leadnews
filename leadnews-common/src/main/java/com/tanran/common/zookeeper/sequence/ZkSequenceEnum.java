package com.tanran.common.zookeeper.sequence;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 这里是需要实现逐渐自增的表名
 * @since 3.0.x 2022/3/19 15:48
 */
@Getter
@AllArgsConstructor
public enum ZkSequenceEnum  {
    /** 描述 */
    AP_LIKES,AP_READ_BEHAVIOR,AP_COLLECTION,AP_USER_FOLLOW,AP_USER_FAN;
}
