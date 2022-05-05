package com.tanran.model.article.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 评论请求dto
 * @since 2022/4/29 10:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentAddDto {
    // 评论的目标id（评论文章即为文章id，对评论进行回复则为评论id）
    private Integer target;
    private String content;
    private Integer articleId;
}
