package com.tanran.model.article.dtos;

import lombok.Data;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/26 16:43
 */
@Data
public class ArticleContentRespDto{
    public Integer articleId;
    public String title;
    public Integer authorId;
    public String authorName;
    private String authorPhoto;
    private boolean isFollowed;
    private Integer attitude;
    private String content;
    private Recomments recomments;
    private boolean collected;
    private Boolean isUnlike;

    public static class Recomments{
        public Integer articleId;
        public String title;
        public String tracking;
    }
}
