package com.tanran.api.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.aliyuncs.utils.StringUtils;
import com.google.common.collect.Lists;
import com.tanran.api.service.ArticleHomeService;
import com.tanran.common.constans.ArticleConstans;
import com.tanran.common.redis.RedisUtils;
import com.tanran.common.result.RespResult;
import com.tanran.model.article.dtos.ArticleHomeDto;
import com.tanran.model.article.dtos.ArticleRequestDto;
import com.tanran.model.article.dtos.ArticleRespDto;
import com.tanran.model.article.pojos.ApArticle;
import com.tanran.model.article.pojos.ApCollection;
import com.tanran.model.behavior.pojos.ApHistories;
import com.tanran.model.common.enums.ErrorCodeEnum;
import com.tanran.model.mappers.app.ApHistoriesMapper;
import com.tanran.model.mappers.app.ArticleContentConfigMapper;
import com.tanran.model.mappers.app.ArticleMapper;
import com.tanran.model.mappers.app.CollectionMapper;
import com.tanran.model.mappers.app.UserArticleListMapper;
import com.tanran.model.user.pojos.ApUser;
import com.tanran.model.user.pojos.ApUserArticleList;
import com.tanran.utils.threadlocal.AppThreadLocalUtils;

import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 首页文章接口实现类
 * @since 3.0.x 2022/3/18 11:25
 */
@Service
@Slf4j
@ComponentScan("com.tanran.common.redis")
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
    @Autowired
    private ArticleContentConfigMapper articleContentConfigMapper;
    @Resource
    private RedisUtils redisUtils;


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

        if (Objects.isNull(dto)) {
            return RespResult.errorResult(ErrorCodeEnum.SERVER_ERROR);
        }

        dto.checkParam();

        List<ApArticle> apArticles = null;
        //加载推荐栏
        if(dto.getChannelId() == 0){
            return getRecommendArticle(dto);
        }

        /** 先加载redis中的数据 */
        apArticles = redisUtils.getCacheList("channnel:"+dto.getChannelId());
        System.out.println("redis查出来的频道数据"+apArticles);
        if(Objects.isNull(apArticles)||apArticles.size()==0){
            apArticles = articleMapper.selectArticleByChannelId(dto);
            /** 将加载出来的数据放到缓存中 */
            if(apArticles.size()>0){
                String key = "channnel:"+dto.getChannelId();
                redisUtils.setCacheList(key,apArticles);
                /**设置过期时间为一天*/
                redisUtils.expire(key,1, TimeUnit.DAYS);
                log.info("==================频道数据添加成功===================");
            }
        }

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

            respResults.add(results);
        }

        ArticleRespDto respDto = new ArticleRespDto(dto.getSize(), dto.getPage(), respResults.size(), respResults);

        return RespResult.okResult(respDto);
    }

    private RespResult getRecommendArticle(ArticleRequestDto dto) {
        /**
         * 根据用户点赞及收藏的标签进行搜索，去除不喜欢的文章
         * 如果用户没有点赞及收藏的文章，就按浏览量进行排序向用户推送
         */
        List<ApCollection> apCollections = collectionMapper.selectCollectionByUserId(dto.getUserId());
        List<Integer> articleIds = articleContentConfigMapper.selectLikesArticleByUserId(dto.getUserId());
        ArrayList<String> lablesList = Lists.newArrayList();
        ArrayList<ApArticle> articles = Lists.newArrayList();
        ArrayList<ArticleRespDto.Results> respResults = Lists.newArrayList();
        if(!Collections.isEmpty(apCollections)){
            List<Integer> collections = apCollections.stream()
                .map(collection -> collection.getEntryId())
                .collect(Collectors.toList());
            List<ApArticle> apCollectionArticles = articleMapper.loadArticleListByIdList(collections);
            apCollectionArticles.stream().map(article -> {
                String[] array = article.getLabels()
                    .split(",");
                List<String> lables = Arrays.asList(array);
                lablesList.addAll(lables);
                return lablesList;
            });
        }
        if(!Collections.isEmpty(articleIds)){
            List<ApArticle> apLikesArticles = articleMapper.loadArticleListByIdList(articleIds);
            apLikesArticles.stream().forEach(article -> {
                String[] array = article.getLabels()
                    .split(",");
                List<String> lables = Arrays.asList(array);
                lablesList.addAll(lables);
            });
        }
        //获取重复度最高的前三个标签
        if(!Collections.isEmpty(lablesList)){
            List<String> lables = lablesList.stream()
                .collect(Collectors.toMap(e -> e, e -> 1, (a, b) -> a + b))
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
            System.out.println("排序后"+lables);
            if(lables.size()>=3){
                lables = lables.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()).subList(0,3);
            }
            System.out.println("查询标签"+lables);
            articles = (ArrayList)articleMapper.selectArticleByLables(lables);
            //查询用户不喜欢的文章
            List<Integer> unLikes = articleContentConfigMapper.selectUnLikeArticleByUserId(dto.getUserId());
            articles = (ArrayList)articles.stream().filter(article -> !unLikes.contains(article.getId())).collect(Collectors.toList());
        }

        List<ApArticle> apArticles = articleMapper.selectArticleByReadCount();
        apArticles.addAll(articles);
        apArticles = apArticles.stream().distinct().collect(Collectors.toList());

        // 组装响应数据
        for (ApArticle article : apArticles){
            String image = article.getImages();
            ArticleRespDto.Cover cover = null;

            if(image != null){
                String[] imags = image.split(",");
                if(image.length()>=1){
                    cover = new ArticleRespDto.Cover(1,imags);
                }
            }else{
                cover = new ArticleRespDto.Cover(0,null);
            }

            ArticleRespDto.Results results = new ArticleRespDto.Results(article.getId(), article.getTitle(), article.getAuthorId(),article.getAuthorName(),article.getComment(),
                article.getPublishTime().toString(),cover,article.getLikes(),article.getCollection());


            respResults.add(results);
        }

        ArticleRespDto respDto = new ArticleRespDto(dto.getSize(), dto.getPage(), respResults.size(), respResults);

        return RespResult.okResult(respDto);
    }

    /**
     *
     * @param dto
     * @param type
     * @return
     */
    private List<ApArticle> getDefaultArticle(ArticleHomeDto dto, Short type) {
        return articleMapper.loadArticleListByLocation(dto,type);
    }

    /**
     *
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
        System.out.println("用户收藏文章传过来的参数"+dto);
        dto.checkParam();
        ApUser user = AppThreadLocalUtils.getUser();
        System.out.println("***************此时的用户信息*****************");
        System.out.println(user);
        if(Objects.nonNull(user)){
            dto.setUserId(user.getId().intValue());
        }

        List<ApCollection> apCollections = collectionMapper.selectCollectionByUser(dto.getUserId(), (short) 0, dto.getPage(), dto.getSize());

        /**获取收藏文章编号集合*/
        List<Integer> articleIds = apCollections.stream()
            .map(s -> s.getEntryId())
            .collect(Collectors.toList());

        if(Collections.isEmpty(articleIds)){
            return null;
        }

        List<ApArticle> apArticles = articleMapper.loadArticleListByIdList(articleIds);


        List<ArticleRespDto.Results> respResults = new ArrayList<>();

        // 组装响应数据
        for (ApArticle article : apArticles){
            String image = article.getImages();
            ArticleRespDto.Cover cover = null;

            if(image != null){
                String[] imags = image.split(",");
                if(image.length()>=1){
                    cover = new ArticleRespDto.Cover(1,imags);
                }
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
    public RespResult userReadHistories(ArticleRequestDto dto) {

        dto.checkParam();
        if(dto.getUserId() == null){
            return RespResult.errorResult(ErrorCodeEnum.PARAM_INVALID);
        }

        List<ApHistories> list = historiesMapper.selectArticleList(dto);

        List<Integer> articleIds = list.stream()
            .map(s -> s.getEntryId())
            .collect(Collectors.toList());
        if(Objects.isNull(articleIds)||articleIds.size()==0){
            return RespResult.okResult(ErrorCodeEnum.SUCCESS);
        }
        List<ApArticle> apArticles = articleMapper.loadArticleListByIdList(articleIds);
        System.out.println("按时间排序前："+apArticles);
        apArticles = apArticles.stream().sorted(Comparator.comparing(ApArticle::getCreatedTime).reversed()).collect(
            Collectors.toList());
        System.out.println("按时间排序后："+apArticles);
        List<ArticleRespDto.Results> respResults = new ArrayList<>();

        // 组装响应数据
        for (ApArticle article : apArticles){

            String image = article.getImages();
            ArticleRespDto.Cover cover = null;

            if(image != null){
                String[] imags = image.split(",");
                System.out.println(image);
                if(image.length()>=1){
                    cover = new ArticleRespDto.Cover(1,imags);
                }
            }else{
                cover = new ArticleRespDto.Cover(0,null);
            }

            ArticleRespDto.Results results = new ArticleRespDto.Results(article.getId(), article.getTitle(), article.getAuthorId(),article.getAuthorName(),article.getComment(),
                article.getPublishTime().toString(),cover,article.getLikes(),article.getCollection());


            respResults.add(results);
        }

        ArticleRespDto respDto = new ArticleRespDto(dto.getSize(), dto.getPage(), respResults.size(), respResults);

        return RespResult.okResult(respDto);
    }
}
