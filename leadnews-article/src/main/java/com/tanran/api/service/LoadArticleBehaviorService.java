package com.tanran.api.service;

import com.tanran.common.result.RespResult;
import com.tanran.model.article.dtos.ArticleInfoDto;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 加载文章详情的初始化配置信息，比如关注、喜欢、不喜欢、阅读位置等
 * @since 3.0.x 2022/3/20 17:18
 */
public interface LoadArticleBehaviorService {
    public RespResult loadArticleBehavior(ArticleInfoDto dto);
}
