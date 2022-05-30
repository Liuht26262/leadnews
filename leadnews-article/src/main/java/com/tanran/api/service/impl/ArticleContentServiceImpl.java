package com.tanran.api.service.impl;

import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tanran.api.service.ArticleContentService;
import com.tanran.common.result.RespResult;
import com.tanran.model.article.dtos.ArticleContentRespDto;
import com.tanran.model.article.pojos.ApArticle;
import com.tanran.model.article.pojos.ApArticleConfig;
import com.tanran.model.article.pojos.ApArticleContent;
import com.tanran.model.article.pojos.ApAuthor;
import com.tanran.model.behavior.pojos.ApHistories;
import com.tanran.model.common.enums.ErrorCodeEnum;
import com.tanran.model.mappers.app.ApHistoriesMapper;
import com.tanran.model.mappers.app.ApUserFollowMapper;
import com.tanran.model.mappers.app.ArticleContentConfigMapper;
import com.tanran.model.mappers.app.ArticleContentMapper;
import com.tanran.model.mappers.app.ArticleMapper;
import com.tanran.model.mappers.app.AuthorMapper;
import com.tanran.model.user.pojos.ApUserFollow;

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
    @Autowired
    private AuthorMapper authorMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ApHistoriesMapper historiesMapper;
    @Autowired
    private ApUserFollowMapper apUserFollowMapper;



    @Override
    public RespResult getArticleContent(Integer articleId,Integer userId) {
        if(articleId==null || articleId < 1){
            return RespResult.errorResult(ErrorCodeEnum.PARAM_REQUIRE);
        }

        ArticleContentRespDto respDto = new ArticleContentRespDto();
        ApArticleConfig apArticleConfig = articleContentConfigMapper.selectArticleContentById(articleId,userId);
        System.out.println("此时文章的配置内容为"+apArticleConfig);
        ApArticle article = articleMapper.selectArticleById(articleId.longValue());
        ApAuthor apAuthor = authorMapper.selectAuthorById(article.getAuthorId());

        if(article==null||apAuthor==null){
            return null;
        }
        if(Objects.isNull(apArticleConfig)){
        //    如果没有关于用户的配置，就设置默认参数
            apArticleConfig = new ApArticleConfig();
            apArticleConfig.setArticleId(articleId);
            apArticleConfig.setIsForward(false);
            apArticleConfig.setIsFollow(false);
            apArticleConfig.setIsCollect(false);
            apArticleConfig.setIsLike(false);
            apArticleConfig.setIsUnlike(false);
            apArticleConfig.setIsDelete(false);
            apArticleConfig.setUserId(userId);
            apArticleConfig.setCreatedTime(new Date(System.currentTimeMillis()));
            apArticleConfig.setUpdatedTime(new Date(System.currentTimeMillis()));
            articleContentConfigMapper.insertSelective(apArticleConfig);
        }


        /*查询文章状态,只有文章没有被删除才会返回文章详情数据*/
        ApArticleContent apArticleContent = articleContentMapper.selectArticleContentById(articleId);

        respDto.setContent(apArticleContent.getContent());
        respDto.setCollected(apArticleConfig.getIsCollect());
        respDto.setArticleId(articleId);
        respDto.setAuthorId(article.getAuthorId());
        respDto.setAuthorName(article.getAuthorName());
        respDto.setAuthorPhoto(apAuthor.getPhoto());
        respDto.setTitle(article.getTitle());
        respDto.setIsUnlike(apArticleConfig.getIsUnlike());
        //暂时不做推荐算法
        respDto.setRecomments(null);
        if(apArticleConfig.getIsLike()){
            respDto.setAttitude(1);
        }else{
            respDto.setAttitude(0);
        }
        respDto.setCollected(apArticleConfig.getIsCollect());

        ApUserFollow apUserFollow = apUserFollowMapper.selectUserFollowByFollowId(apArticleConfig.getUserId()
            .longValue(), article.getAuthorId());
        if(Objects.isNull(apUserFollow)){
            respDto.setFollowed(false);
        }else {
            respDto.setFollowed(true);
        }
        /**
         *更新阅读数量
         */
        ApArticle apArticle = articleMapper.selectArticleById(articleId.longValue());
        apArticle.setViews(apArticle.getViews()+1);
        articleMapper.updateViews(articleId.longValue(),apArticle.getViews());

        /**
         * 添加数据到用户阅读历史表中
         * */
        ApHistories apHistories = new ApHistories();

        apHistories.setBehaviorEntryId(1);
        apHistories.setEntryId(articleId);
        apHistories.setCreatedTime(new Date(System.currentTimeMillis()));
        apHistories.setPublishedTime(new Date(System.currentTimeMillis()));

        historiesMapper.insertSelective(apHistories);

        return RespResult.okResult(respDto);
    }
}
