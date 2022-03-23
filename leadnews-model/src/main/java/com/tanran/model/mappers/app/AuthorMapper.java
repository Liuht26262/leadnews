package com.tanran.model.mappers.app;

import com.tanran.model.article.pojos.ApAuthor;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 3.0.x 2022/3/20 17:12
 */
public interface AuthorMapper {
    public ApAuthor selectAuthorById(Integer id);
}
