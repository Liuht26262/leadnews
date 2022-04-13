package com.tanran.common.common.pojo;

import java.util.Date;

import lombok.Data;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/4 13:52
 */
@Data
public class EsIndexEntity {
    private Long id;
    private String content;
    private Long channelId;
    //    private Date pub_time;
    private Date publishTime;
    private Long status;
    private String title;
    private Long userId;
    private String tag;
}