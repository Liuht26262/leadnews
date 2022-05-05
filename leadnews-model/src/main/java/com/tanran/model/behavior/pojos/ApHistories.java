package com.tanran.model.behavior.pojos;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class ApHistories implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 实体ID
     */
    private Integer behaviorEntryId;

    /**
     * 文章ID
     */
    private Integer entryId;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 发布时间
     */
    private Date publishedTime;
}