package com.tanran.api.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.tanran.api.service.LoadArticleBehaviorService;
import com.tanran.common.result.RespResult;
import com.tanran.model.article.dtos.ArticleInfoDto;
import com.tanran.model.article.pojos.ApAuthor;
import com.tanran.model.article.pojos.ApCollection;
import com.tanran.model.behavior.pojos.ApBehaviorEntry;
import com.tanran.model.behavior.pojos.ApLikesBehavior;
import com.tanran.model.behavior.pojos.ApUnlikesBehavior;
import com.tanran.model.common.enums.AppHttpCodeEnum;
import com.tanran.model.mappers.app.AuthorMapper;
import com.tanran.model.mappers.app.BehaviorEntryMapper;
import com.tanran.model.mappers.app.CollectionMapper;
import com.tanran.model.mappers.app.LikeBehaviorMapper;
import com.tanran.model.mappers.app.UserFollowMapper;
import com.tanran.model.user.pojos.ApUser;
import com.tanran.model.user.pojos.ApUserFollow;
import com.tanran.utils.threadlocal.AppThreadLocalUtils;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 加载文章详情的初始化配置信息，比如关注、喜欢、不喜欢、阅读位置等
 * @since 3.0.x 2022/3/20 17:20
 */
@Service
public class LoadArticleBehaviorServiceImpl implements LoadArticleBehaviorService {
    @Autowired
    private BehaviorEntryMapper behaviorEntryMapper;
    @Autowired
    private CollectionMapper collectionMapper;
    @Autowired
    private AuthorMapper authorMapper;
    @Autowired
    private LikeBehaviorMapper likeBehaviorMapper;
    @Autowired
    private UserFollowMapper userFollowMapper;



    @Override
    public RespResult loadArticleBehavior(ArticleInfoDto dto) {
        ApUser user = AppThreadLocalUtils.getUser();
        //用户和设备不能同时为空，否则就返回参数错误
        if(ObjectUtils.isEmpty(user)&&dto.getEquipmentId()==null){
            return RespResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        Long userId = null;
        if(user!=null){
            userId = user.getId();
        }

        /*查询用户行为实体*/
        ApBehaviorEntry behaviorEntry = behaviorEntryMapper.selectByUserIdOrEquipment(userId, dto.getEquipmentId());
        if(ObjectUtils.isEmpty(behaviorEntry)){
            return RespResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }

        /*定义标签数据*/
        Boolean isUserFolleow = false;
        Boolean isCollection = false;
        Boolean isLike = false;
        Boolean isUnLike = false;

        /*判断用户是否喜欢*/
        ApLikesBehavior apLikesBehavior = likeBehaviorMapper.selectLastLike(behaviorEntry.getId(), dto.getArticleId(),
            ApCollection.Type.ARTICLE.getCode());
        if(apLikesBehavior!=null && apLikesBehavior.getOperation() == ApLikesBehavior.Operation.LIKE.getCode()){
            isLike = true;
        }

        /*判断用户是否不喜欢*/
        ApUnlikesBehavior apUnlikesBehavior = likeBehaviorMapper.selectLastUnLike(behaviorEntry.getEntryId(),
            dto.getArticleId());
        if(apLikesBehavior!=null && apUnlikesBehavior.getType() == ApUnlikesBehavior.Type.UNLIKE.getCode()){
            isUnLike = true;
        }

        /*判断用户是否收藏*/
        ApCollection apCollection = collectionMapper.selectCollectionForEntryId(behaviorEntry.getId(),
            dto.getArticleId(), ApCollection.Type.ARTICLE.getCode());
        if(!ObjectUtils.isEmpty(apCollection)){
            isCollection = true;
        }

        /*判断是否关注,根据用户id和作者的id进行查询，如果能在作者表中查到user就说明关注了*/
        ApAuthor apAuthor = authorMapper.selectAuthorById(dto.getAuthorId());
        if(user!=null&&apAuthor!=null&apAuthor.getId()!=null){
            ApUserFollow apUserFollow = userFollowMapper.selectByFollowId(userId, apAuthor.getId());
            if(apUserFollow!=null){
                isUserFolleow = true;
            }
        }

        HashMap<String, Boolean> dataMap = new HashMap<>();
        dataMap.put("isfollow",isUserFolleow);
        dataMap.put("islike",isLike);
        dataMap.put("isunLike",isUnLike);
        dataMap.put("iscollection",isCollection);

        return RespResult.okResult(dataMap);
    }
}
