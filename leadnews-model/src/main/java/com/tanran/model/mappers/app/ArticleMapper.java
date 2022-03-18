package com.tanran.model.mappers.app;


import com.tanran.model.article.dtos.ArticleHomeDto;
import com.tanran.model.article.pojos.ApArticle;
import com.tanran.model.article.pojos.ApArticleSDto;
import com.tanran.model.user.pojos.ApUserArticleList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
    List<ApArticle> loadArticleListByLocation(@Param("dto") ArticleHomeDto dto, @Param("type") short type);

    /**
     * 依据文章IDS来获取文章详细内容
     *
     * @param list 文章ID
     * @return
     */
    List<ApArticle> loadArticleListByIdList(@Param("list") List<ApUserArticleList> list);



}