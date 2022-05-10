package com.tanran.model.mappers.app;

import com.tanran.model.article.pojos.ApArticleContent;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 3.0.x 2022/3/19 22:01
 */
public interface ArticleContentMapper {
    ApArticleContent selectArticleContentById(Integer articleId);
    void insertSelective(ApArticleContent articleContent);

    Integer deleteByArticleId(Integer articleId);
}
