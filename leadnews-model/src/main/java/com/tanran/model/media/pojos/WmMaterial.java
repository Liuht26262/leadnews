package com.tanran.model.media.pojos;

import com.tanran.model.annotation.IdEncrypt;

import lombok.Data;

import java.util.Date;

@Data
public class WmMaterial {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 自媒体用户ID
     */
    @IdEncrypt
    private Integer userId;

    /**
     * 图片地址
     */
    private String url;

    /**
     * 素材类型
     0 图片
     1 视频
     */
    private Short type;

    /**
     * 是否收藏
     */
    private Short isCollection;

    /**
     * 创建时间
     */
    private Date createdTime;
}