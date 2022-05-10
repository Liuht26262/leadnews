package com.tanran.model.article.pojos;

import java.util.Date;

import com.tanran.model.annotation.DateConvert;

import lombok.Data;

@Data
public class ApAuthor {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 作者名称
     */
    private String name;

    /**
     *
     */
    private String photo;

    /**
     * 0 管理员 1 媒体
     */
    private Short type;

    /**
     * id
     */
    private Integer userId;

    /**
     * 创建时间
     */
    @DateConvert("yyyyMMddHHmmss")
    private Date createdTime;
}