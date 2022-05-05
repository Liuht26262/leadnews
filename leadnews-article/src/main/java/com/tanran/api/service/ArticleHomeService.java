package com.tanran.api.service;

import com.tanran.common.result.RespResult;
import com.tanran.model.article.dtos.ArticleHomeDto;
import com.tanran.model.article.dtos.ArticleRequestDto;
import com.tanran.model.common.dtos.PageRequestDto;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 3.0.x 2022/3/18 11:09
 */
public interface ArticleHomeService {
    /**
     *
     * @param dto
     * @param type  1 加载更多  2 加载更新
     * @return
     */
    public RespResult load(ArticleHomeDto dto, Short type);

    RespResult loadArticle(ArticleRequestDto dto);

    RespResult userCollection(ArticleRequestDto dto);

    RespResult userReadHistories(Integer userId, PageRequestDto dto);
}
