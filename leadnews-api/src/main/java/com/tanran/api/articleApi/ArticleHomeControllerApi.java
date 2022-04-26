package com.tanran.api.articleApi;

import com.tanran.common.result.RespResult;
import com.tanran.model.article.dtos.ArticleHomeDto;
import com.tanran.model.article.dtos.ArticleRequestDto;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @since 3.0.x 2022/3/18 10:23
 */
public interface ArticleHomeControllerApi {

    /**
     * 加载首页文章
     * @param dto
     * @return
     */
    public RespResult load(ArticleHomeDto dto);

    /**
     * 加载更多
     * @param dot
     * @return
     */
    public RespResult loadMore(ArticleHomeDto dot);

    /**
     * 加载最新的文章信息
     * @param dto
     * @return
     */
    public RespResult loadNew(ArticleHomeDto dto);

    /**
     * 加载文章
     * */
    public RespResult loadArticle(ArticleRequestDto dto);

}
