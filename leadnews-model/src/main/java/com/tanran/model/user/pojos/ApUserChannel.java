package com.tanran.model.user.pojos;

import java.util.Date;

import lombok.Data;

@Data
public class ApUserChannel {
    /**
     *
     */
    private Integer id;

    /**
     * 频道ID
     */
    private Integer channelId;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     *
     */
    private String channelName;

    /**
     * 频道排序
     */
    private Byte ord;

    /**
     * 登录时间
     */
    private Date createdTime;
}