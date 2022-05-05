package com.tanran.model.article.pojos;

import java.util.Date;

import com.tanran.model.annotation.IdEncrypt;

import lombok.Data;
/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 文章配置类
 * @since 3.0.x 2022/3/19 22:01
 */

@Data
public class ApArticleConfig {
    private Long id;
    // 增加注解，JSON序列化时自动混淆加密
    @IdEncrypt
    private Integer articleId;

    private Integer userId;

    /**
     * 是否可评论
     */
    private Boolean isComment;

    /**
     * 是否转发
     */
    private Boolean isForward;

    /**
     * 是否下架
     */
    private Boolean isDown;

    /**
     * 是否已删除
     */
    private Boolean isDelete;

    /**
     * 是否收藏
     */
    private Boolean isCollect;

    /**
     * 是否关注
     */
    private Boolean isFollow;

    /**
     *
     */
    private Boolean isLike;

    /**
     *
     */
    private Boolean isUnlike;

    /**
     * 发布时间
     * */
    private Date createdTime;

    /**
     * 更新时间
     */
    private Date updatedTime;

}