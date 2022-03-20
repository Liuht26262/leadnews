package com.tanran.model.article.pojos;

import com.tanran.model.annotation.IdEncrypt;

import lombok.Data;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 文章内容
 * @since 3.0.x 2022/3/19 19:18
 */

@Data
public class ApArticleContent {
    private Integer id;
    // 增加注解，JSON序列化时自动混淆加密
    @IdEncrypt
    private Integer articleId;
    private String content;

}