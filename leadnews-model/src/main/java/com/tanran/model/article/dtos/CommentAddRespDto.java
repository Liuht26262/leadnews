package com.tanran.model.article.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/29 10:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentAddRespDto {
    /**新建的评论id*/
    private Integer commentId;
    /**评论所属的目标id*/
    private Integer target;
    /**文章Id*/
    private Integer articleId;
}
