package com.tanran.model.admin.pojos;

import java.util.Date;

import lombok.Data;

@Data
public class AdChannel {
    /**
     *
     */
    private Integer id;

    /**
     * 频道名称
     */
    private String channelName;

    /**
     * 频道描述
     */
    private String description;

    /**
     * 是否默认频道
     */
    private Boolean isDefault;

    /**
     *
     */
    private Boolean status;

    /**
     * 默认排序
     */
    private Byte ord;

    /**
     * 创建时间
     */
    private Date createdTime;

}