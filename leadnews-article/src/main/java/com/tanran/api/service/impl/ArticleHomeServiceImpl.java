package com.tanran.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.aliyuncs.utils.StringUtils;
import com.tanran.api.service.ArticleHomeService;
import com.tanran.common.constans.ArticleConstans;
import com.tanran.common.result.RespResult;
import com.tanran.model.article.dtos.ArticleHomeDto;
import com.tanran.model.article.dtos.ArticleRequestDto;
import com.tanran.model.article.dtos.ArticleRespDto;
import com.tanran.model.article.pojos.ApArticle;
import com.tanran.model.article.pojos.ApCollection;
import com.tanran.model.behavior.pojos.ApHistories;
import com.tanran.model.common.dtos.PageRequestDto;
import com.tanran.model.common.enums.ErrorCodeEnum;
import com.tanran.model.mappers.app.ApHistoriesMapper;
import com.tanran.model.mappers.app.ArticleMapper;
import com.tanran.model.mappers.app.CollectionMapper;
import com.tanran.model.mappers.app.UserArticleListMapper;
import com.tanran.model.user.pojos.ApUser;
import com.tanran.model.user.pojos.ApUserArticleList;
import com.tanran.utils.threadlocal.AppThreadLocalUtils;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 首页文章接口实现类
 * @since 3.0.x 2022/3/18 11:25
 */
@Service
public class ArticleHomeServiceImpl implements ArticleHomeService {

    // 单页最大加载的数字
    private final  static short MAX_PAGE_SIZE = 50;

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserArticleListMapper userArticleListMapper;
    @Autowired
    private CollectionMapper collectionMapper;
    @Autowired
    private ApHistoriesMapper historiesMapper;



    /**
     *
     * @param dto
     * @param type  1 加载更多  2 加载更新
     * @return
     */
    @Override
    public RespResult load(ArticleHomeDto dto,Short type) {
        /**获取当前用户信息*/
        ApUser user = AppThreadLocalUtils.getUser();
        if(ObjectUtils.isEmpty(dto)){
            dto = new ArticleHomeDto();
        }
        Integer size = dto.getSize();
        String tag = dto.getTag();
        // 分页参数校验
        if (size == null || size <= 0) {
            size = 20;
        }
        dto.setSize(size);
        size = Math.min(size,MAX_PAGE_SIZE);
        //  类型参数校验
        if (!type.equals(ArticleConstans.LOADTYPE_LOAD_MORE) && !type.equals(ArticleConstans.LOADTYPE_LOAD_NEW)) {
            type = ArticleConstans.LOADTYPE_LOAD_MORE;
        }
        // 文章频道参数验证
        if (StringUtils.isEmpty(tag)) {
            dto.setTag(ArticleConstans.DEFAULT_TAG);
        }
        // 最大时间处理
        if(dto.getMaxBehotTime()==null){
            dto.setMaxBehotTime(new Date());
        }
        // 最小时间处理
        if(dto.getMinBehotTime()==null){
            dto.setMinBehotTime(new Date());
        }
        // 数据加载
        if(user!=null){
            return RespResult.okResult(getUserArticle(user,dto,type));
        }else{
            return RespResult.okResult(getDefaultArticle(dto,type));
        }
    }

    /**
     * 获取指定频道的文章列表
     * */
    @Override
    public RespResult loadArticle(ArticleRequestDto dto) {

        System.out.println("传入的数据"+dto);

        if (Objects.isNull(dto)) {
            return RespResult.errorResult(ErrorCodeEnum.SERVER_ERROR);
        }

        dto.checkParam();

        List<ApArticle> apArticles = articleMapper.selectArticleByChannelId(dto);

        System.out.println(apArticles);

        List<ArticleRespDto.Results> respResults = new ArrayList<>();

        // 组装响应数据
        for (ApArticle article : apArticles){
            String image = article.getImages();
            ArticleRespDto.Cover cover = null;

            if(image != null){
                String[] imags = image.split(",");
                cover = new ArticleRespDto.Cover(1,imags);
            }else{
                cover = new ArticleRespDto.Cover(0,null);
            }

            ArticleRespDto.Results results = new ArticleRespDto.Results(article.getId(), article.getTitle(), article.getAuthorId(),article.getAuthorName(),article.getComment(),
                article.getPublishTime().toString(),cover,article.getLikes(),article.getCollection());

            System.out.println(results.toString());

            respResults.add(results);
        }

        ArticleRespDto respDto = new ArticleRespDto(dto.getSize(), dto.getPage(), respResults.size(), respResults);

        return RespResult.okResult(respDto);
    }


    /**
     * 加载默认的文章信息
     * @param dto
     * @param type
     * @return
     */
    private List<ApArticle> getDefaultArticle(ArticleHomeDto dto, Short type) {
        return articleMapper.loadArticleListByLocation(dto,type);
    }

    /**
     * 先从用户的推荐表中查找文章信息，如果没有再从默认文章信息获取数据
     * @param user
     * @param dto
     * @param type
     * @return
     */
    private List<ApArticle> getUserArticle(ApUser user, ArticleHomeDto dto, Short type) {
        List<ApUserArticleList> list = userArticleListMapper.loadArticleIdListByUser(user,dto,type);
        if(!list.isEmpty()){
            List<Integer> articles = list.stream()
                .map(s -> s.getArticleId())
                .collect(Collectors.toList());
            return articleMapper.loadArticleListByIdList(articles);
        }else{
            return getDefaultArticle(dto,type);
        }
    }




    /**
     *加载用户收藏的新闻
     * @param dto
     * @return
     */
    @Override
    public RespResult userCollection(ArticleRequestDto dto) {
        dto.checkParam();
        ApUser user = AppThreadLocalUtils.getUser();
        System.out.println("***************此时的用户信息*****************");
        System.out.println(user);
        if(dto.getUserId() == null){
            dto.setUserId(user.getId().intValue());
        }

        List<ApCollection> apCollections = collectionMapper.selectCollectionByUser(dto.getUserId(), (short) 1, dto.getPage(), dto.getSize());

        /**获取收藏文章编号集合*/
        List<Integer> articleIds = apCollections.stream()
            .map(s -> s.getEntryId())
            .collect(Collectors.toList());

        List<ApArticle> apArticles = articleMapper.loadArticleListByIdList(articleIds);


        List<ArticleRespDto.Results> respResults = new ArrayList<>();

        // 组装响应数据
        for (ApArticle article : apArticles){
            String image = article.getImages();
            ArticleRespDto.Cover cover = null;

            if(image != null){
                String[] imags = image.split(",");
                cover = new ArticleRespDto.Cover(1,imags);
            }else{
                cover = new ArticleRespDto.Cover(0,null);
            }

            ArticleRespDto.Results results = new ArticleRespDto.Results(article.getId(), article.getTitle(), article.getAuthorId(),article.getAuthorName(),article.getComment(),
                article.getPublishTime().toString(),cover,article.getLikes(),article.getCollection());

            System.out.println(results.toString());

            respResults.add(results);
        }

        ArticleRespDto respDto = new ArticleRespDto(dto.getSize(), dto.getPage(), respResults.size(), respResults);

        return RespResult.okResult(respDto);
    }

    @Override
    public RespResult userReadHistories(Integer userId, PageRequestDto dto) {

        dto.checkParam();
        if(userId == null){
            return RespResult.errorResult(ErrorCodeEnum.PARAM_INVALID);
        }

        List<ApHistories> list = historiesMapper.selectArticleList(userId,dto);

        List<Integer> articleIds = list.stream()
            .map(s -> s.getEntryId())
            .collect(Collectors.toList());

        List<ApArticle> apArticles = articleMapper.loadArticleListByIdList(articleIds);


        List<ArticleRespDto.Results> respResults = new ArrayList<>();

        // 组装响应数据
        for (ApArticle article : apArticles){
            String image = article.getImages();
            ArticleRespDto.Cover cover = null;

            if(image != null){
                String[] imags = image.split(",");
                cover = new ArticleRespDto.Cover(1,imags);
            }else{
                cover = new ArticleRespDto.Cover(0,null);
            }

            ArticleRespDto.Results results = new ArticleRespDto.Results(article.getId(), article.getTitle(), article.getAuthorId(),article.getAuthorName(),article.getComment(),
                article.getPublishTime().toString(),cover,article.getLikes(),article.getCollection());

            System.out.println(results.toString());

            respResults.add(results);
        }

        ArticleRespDto respDto = new ArticleRespDto(dto.getSize(), dto.getPage(), respResults.size(), respResults);

        return RespResult.okResult(respDto);
    }
}
