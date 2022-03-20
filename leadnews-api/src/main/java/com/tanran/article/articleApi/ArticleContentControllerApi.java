package com.tanran.article.articleApi;

import com.tanran.common.result.RespResult;
import com.tanran.model.article.dtos.ArticleInfoDto;
import com.tanran.model.common.dtos.ResponseResult;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 文章详情Api
 * @since 3.0.x 2022/3/19 22:20
 */
public interface ArticleContentControllerApi {
    /**
     * 加載首頁详情
     * @param dto 封装参数对象
     * @return 文章详情
     */
    RespResult loadArticleContent(ArticleInfoDto dto);

    /**
     * 加载文章详情的行为内容
     * @param dto
     * @return
     */
    RespResult loadArticleBehavior( ArticleInfoDto dto);
}
