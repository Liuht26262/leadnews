package com.tanran.model.mappers.app;

import com.tanran.model.article.pojos.ApArticleConfig;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 文章配置查询
 * @since 3.0.x 2022/3/20 15:00
 */
public interface ArticleContentConfigMapper {
    public ApArticleConfig selectArticleContentById(Integer articleId);
}
