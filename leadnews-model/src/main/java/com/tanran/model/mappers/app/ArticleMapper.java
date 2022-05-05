package com.tanran.model.mappers.app;


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

    /**
     * 照用户地理位置，加载文章
     *
     * @param dto  参数封装对象
     * @param type 加载方向
     * @return
     */
    List<ApArticle> loadArticleListByLocation(@Param("dto") ArticleHomeDto dto, @Param("type") Short type);

    /**
     * 依据文章IDS来获取文章详细内容
     *
     * @param list 文章ID
     * @return
     */
    List<ApArticle> loadArticleListByIdList(@Param("list") List<Integer> list);

    ApArticle selectArticleById(Long id);

    void insert(ApArticle article);

    void insertSelective(ApArticle article);

    List<ApArticle> selectArticleByChannelId(@Param("dto") ArticleRequestDto dto);
}