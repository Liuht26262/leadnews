package com.tanran.api.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.tanran.api.service.ArticleContentService;
import com.tanran.common.result.RespResult;
import com.tanran.model.article.pojos.ApArticleConfig;
import com.tanran.model.article.pojos.ApArticleContent;
import com.tanran.model.common.enums.AppHttpCodeEnum;
import com.tanran.model.mappers.app.ArticleContentConfigMapper;
import com.tanran.model.mappers.app.ArticleContentMapper;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 加载文章详情实现
 * @since 3.0.x 2022/3/19 22:18
 * 使用Map进行封装，其中格式如下：
 * {   “config”:// ApArticleConfig   ，“content”://ApArticleContent   }   注意：如果文章已经删除，content属性将不返回
 */
@Service
public class ArticleContentServiceImpl implements ArticleContentService {
    @Autowired
    private ArticleContentMapper articleContentMapper;
    @Autowired
    private ArticleContentConfigMapper articleContentConfigMapper;

    @Override
    public RespResult getArticleContent(Integer articleId) {
        if(articleId==null || articleId < 1){
            return RespResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }

        /*返回方式为Map*/
        HashMap<String, Object> dataMap = new HashMap<>();

        ApArticleConfig apArticleConfig = articleContentConfigMapper.selectArticleContentById(articleId);
        if(ObjectUtils.isEmpty(apArticleConfig)){
            return RespResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }else if(!apArticleConfig.getIsDelete()){
            /*查询文章状态,只有文章没有被删除才会返回文章详情数据*/
            ApArticleContent apArticleContent = articleContentMapper.selectArticleContentById(articleId);

            /*执行编码格式转换，需要解压*/
            // String content = ZipUtils.gunzip(apArticleContent.getContent());
            // dataMap.put("content",content);

            dataMap.put("content",apArticleContent.getContent());
        }
        dataMap.put("config",apArticleConfig);

        return RespResult.okResult(dataMap);
    }
}
