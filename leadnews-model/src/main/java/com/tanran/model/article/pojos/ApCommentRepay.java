package com.tanran.model.article.pojos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class ApCommentRepay implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer authorId;

    /**
     * 用户昵称
     */
    private String authorName;

    /**
     * 评论的id
     */
    private Integer commentId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 
     */
    private Integer likes;

    /**
     * 
     */
    private BigDecimal longitude;

    /**
     * 维度
     */
    private BigDecimal latitude;

    /**
     * 地理位置
     */
    private String address;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新时间
     */
    private Date updatedTime;

    /**
     * 点赞数量
     */
    private Integer likecount;

    /**
     * 回复数量
     */
    private Integer replyCount;

    /**
     * 是否点赞
     */
    private Byte isLiking;

    /**
     * 文章id
     */
    private Integer articleId;

    /**
     * 作者头像
     */
    private String image;
}