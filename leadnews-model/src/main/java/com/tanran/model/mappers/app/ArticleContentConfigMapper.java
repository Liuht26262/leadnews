package com.tanran.model.mappers.app;

import org.apache.ibatis.annotations.Param;

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
    public ApArticleConfig selectArticleContentById(@Param("articleId") Integer articleId,@Param("userId") Integer userId);

    void insertSelective(ApArticleConfig articleConfig);

    int deleteByPrimaryKey(Integer id);

    int insert(ApArticleConfig record);

    ApArticleConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ApArticleConfig record);

    int updateByPrimaryKey(ApArticleConfig record);

    ApArticleConfig findConfigById(@Param("articleId") Integer articleId, @Param("userId") Integer userId);
}
