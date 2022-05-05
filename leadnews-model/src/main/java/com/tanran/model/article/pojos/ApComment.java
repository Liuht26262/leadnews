package com.tanran.model.article.pojos;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class ApComment {
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
     * 
     */
    private Integer entryId;

    /**
     * 频道ID
     */
    private Integer channelId;

    /**
     * 评论内容类型
            'a' 文章
            'c'评论
     */
    private String type;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 
     */
    private String image;

    /**
     * 
     */
    private Integer reply;

    /**
     * 文章标记
            0 普通评论
            1 热点评论
            2 推荐评论
            3 置顶评论
     */
    private Byte flag;

    /**
     * 
     */
    private BigDecimal longitude;

    /**
     * 维度
     */
    private BigDecimal latitude;

    /**
     * 评论排列序号
     */
    private Integer ord;

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

}