package com.tanran.model.article.pojos;

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
    private Boolean isComment;
    private Boolean isForward;
    private Boolean isDown;
    private Boolean isDelete;
}