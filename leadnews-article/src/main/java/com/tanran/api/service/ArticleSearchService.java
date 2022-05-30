package com.tanran.api.service;

import java.util.List;

import com.tanran.common.result.RespResult;
import com.tanran.model.article.dtos.UserSearchDto;
import com.tanran.model.user.pojos.ApUserSearch;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/3/31 9:17
 */
public interface ArticleSearchService {
    RespResult<List<ApUserSearch>> selectUserSearch(UserSearchDto dto);

    RespResult deleteUserSearch(UserSearchDto dto);

    RespResult clearUserSearch(UserSearchDto dto);

    RespResult articleSearchBydto(UserSearchDto dto);

    RespResult loadHotKeyWords(UserSearchDto dto);

    RespResult selectAssociateWords(UserSearchDto dto);

    RespResult saveUserSearch(Integer entryId,String keyWords);

    RespResult articleSearch(UserSearchDto dto);


}
