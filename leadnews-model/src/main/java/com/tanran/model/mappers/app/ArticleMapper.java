package com.tanran.model.mappers.app;


import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tanran.model.article.dtos.ArticleHomeDto;
import com.tanran.model.article.dtos.ArticleRequestDto;
import com.tanran.model.article.pojos.ApArticle;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @since 3.0.x 2022/3/18 12:16
 */

public interface ArticleMapper {

    List<ApArticle> loadArticleListByLocation(@Param("dto") ArticleHomeDto dto, @Param("type") Short type);

    List<ApArticle> loadArticleListByIdList(@Param("list") List<Integer> list);

    ApArticle selectArticleById(Long id);

    void insert(ApArticle article);

    void insertSelective(ApArticle article);

    List<ApArticle> selectArticleByChannelId(@Param("dto") ArticleRequestDto dto);

    Integer selectByDate(String title, Date createdTime);

    void deleteArticleById(Integer articleId);

    List<ApArticle> selectArticleByLables(@Param("lables") List lables);

    List<ApArticle> selectArticleByReadCount();

    void updateViews(@Param("articleId") Long articleTd,@Param("views") Integer views);

    List<ApArticle> selectArticle(String searchWords);
}