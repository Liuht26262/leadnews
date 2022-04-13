package com.tanran.model.mappers.app;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tanran.model.article.pojos.ApHotWords;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/3/31 10:44
 */
public interface HotKeyWordsMapper {
    List<ApHotWords> loadHotKeyWords(String hotDate);

}
