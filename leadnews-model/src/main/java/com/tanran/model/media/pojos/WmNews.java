package com.tanran.model.media.pojos;

import java.util.Date;

import lombok.Data;

@Data
public class WmNews {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 作者名
     */
    private String authorName;

    /**
     * 作者id
     */
    private Integer userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 文章布局
     0 无图文章
     1 单图文章
     2 多图文章
     */
    private Short type;

    /**
     * 图文频道ID
     */
    private Integer channelId;

    /**
     *
     */
    private String labels;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 提交时间
     */
    private Date submitedTime;

    /**
     * 当前状态
     0 草稿
     1 提交（待审核）
     2 审核失败
     8 审核通过（待发布）
     9 已发布
     */
    private Short status;

    /**
     * 定时发布时间，不定时则为空
     */
    private Date publishTime;

    /**
     * 拒绝理由
     */
    private String reason;

    /**
     * 发布库文章ID
     */
    private Integer articleId;

    /**
     * //图片用逗号分隔
     */
    private String images;

    /**
     * 发布标识符
     */
    private Short enable;

    /**
     * 图文内容
     */
    private String content;
}