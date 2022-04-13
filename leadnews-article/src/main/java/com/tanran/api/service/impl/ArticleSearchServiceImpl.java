package com.tanran.api.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang.time.DateFormatUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tanran.api.service.ArticleSearchService;
import com.tanran.common.result.RespResult;
import com.tanran.model.article.dtos.UserSearchDto;
import com.tanran.model.article.pojos.ApArticle;
import com.tanran.model.behavior.pojos.ApBehaviorEntry;
import com.tanran.model.common.constants.ESIndexConstants;
import com.tanran.model.common.enums.ErrorCodeEnum;
import com.tanran.model.mappers.app.ArticleMapper;
import com.tanran.model.mappers.app.AssociateWordsMapper;
import com.tanran.model.mappers.app.BehaviorEntryMapper;
import com.tanran.model.mappers.app.HotKeyWordsMapper;
import com.tanran.model.mappers.app.UserSearchMapper;
import com.tanran.model.user.pojos.ApUser;
import com.tanran.model.user.pojos.ApUserSearch;
import com.tanran.utils.threadlocal.AppThreadLocalUtils;

import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 用户搜索业务实现类
 * @since 2022/3/31 9:21
 */
@Service
@SuppressWarnings("all")
public class ArticleSearchServiceImpl implements ArticleSearchService {
    @Autowired
    private BehaviorEntryMapper apBehaviorEntryMapper;
    @Autowired
    private UserSearchMapper userSearchMapper;
    @Autowired
    private HotKeyWordsMapper hotKeyWordsMapper;
    @Autowired
    private AssociateWordsMapper associateWordsMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private JestClient jestClient;

    public RespResult getEntryId(UserSearchDto dto){
        ApUser user = AppThreadLocalUtils.getUser();
        if (Objects.isNull(user)&&Objects.isNull(dto.getEquipmentId())){
            return RespResult.errorResult(ErrorCodeEnum.PARAM_REQUIRE);
        }
        Long id = null;
        if(!Objects.isNull(user)){
            id = user.getId();
        }

        ApBehaviorEntry behaviorEntry = apBehaviorEntryMapper.selectByUserIdOrEquipment(id, dto.getEquipmentId());
        if(Objects.isNull(behaviorEntry)){
            return RespResult.errorResult(ErrorCodeEnum.PARAM_REQUIRE);
        }

        return RespResult.okResult(behaviorEntry.getEntryId());
    }
    /**
     * - 判断入参articleId是否合法，不合法则返回PARAM_INVALID错误
     * - 查询文章对应的ApArticleConfig配置信息
     * - 如果未查询到ApArticleConfig信息，则返回PARAM_INVALID错误
     * - 如果文章未被删除，则查找处理文章内容对象
     * - 封装响应DTO返回数据
     * */
    @Override
    public RespResult<List<ApUserSearch>> selectUserSearch(UserSearchDto dto) {
      if (dto.getPageSize()>50){
          return RespResult.errorResult(ErrorCodeEnum.PARAM_INVALID);
      }
        RespResult entryId = getEntryId(dto);

        List<ApUserSearch> userSearcheList = userSearchMapper.selectUserSearch((Integer) entryId.getData(),dto.getPageSize());

        return RespResult.okResult(userSearcheList);
    }

    @Override
    public RespResult deleteUserSearch(UserSearchDto dto) {
        if(dto.getHisList() == null){
            return RespResult.errorResult(ErrorCodeEnum.PARAM_INVALID);
        }
        RespResult entryId = getEntryId(dto);
        List<Integer> idList = dto.getHisList()
            .stream()
            .map(list -> list.getId())
            .collect(Collectors.toList());

        return RespResult.okResult(userSearchMapper.deleteUserSearch((Integer) entryId.getData(), idList));

    }

    @Override
    public RespResult clearUserSearch(UserSearchDto dto) {
        RespResult entryId = getEntryId(dto);
        return RespResult.okResult(userSearchMapper.clearUserSearch((Integer)entryId.getData()));
    }

    /**查询今日热词*/
    @Override
    public RespResult loadHotKeyWords(UserSearchDto dto) {
        String hotDate = dto.getHotDate();
        if (Objects.isNull(hotDate)){
            hotDate = DateFormatUtils.format(new Date(),"yyyy-MM-dd");
        }
        return RespResult.okResult(hotKeyWordsMapper.loadHotKeyWords(hotDate));
    }

    @Override
    public RespResult selectAssociateWords(UserSearchDto dto) {
        if(dto.getPageSize()>50||dto.getPageSize()<0){
            return RespResult.errorResult(ErrorCodeEnum.PARAM_INVALID);
        }
        String s = "%"+dto.getSearchWords()+"%";
        System.out.println("*****************************************");
        System.out.println(dto);
        System.out.println("*****************************************");
        return RespResult.okResult(associateWordsMapper.selectAssociateWords(s,dto.getPageSize()));
    }

    @Override
    public RespResult saveUserSearch(Integer entryId, String keyWords) {
        //确定索引是否存在
        int i = userSearchMapper.checkSearch(entryId, keyWords);
        if (i>0){
            return RespResult.okResult(1);
        }
        ApUserSearch userSearch = ApUserSearch.builder()
            .entryId(entryId)
            .keyword(keyWords)
            .build();
        return RespResult.okResult(userSearchMapper.saveUserSearch(userSearch));
    }

    @Override
    public RespResult articleSearch(UserSearchDto dto) {
        //如果是第一页就保存搜索记录
        if(dto.getFromIndex() == 0){
            RespResult result = getEntryId(dto);
            if(result.getCode() == 200){
                saveUserSearch((Integer) result.getData(),dto.getSearchWords());
            }
        }
        //根据关键字查询索引库
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("title",dto.getSearchWords()));
        //设置分页
        searchSourceBuilder.from(dto.getFromIndex());
        searchSourceBuilder.size(dto.getPageSize());
        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(ESIndexConstants.ARTICLE_INDEX).addType(ESIndexConstants.DEFAULT_DOC).build();
        try {
            SearchResult searchResult = jestClient.execute(search);
            List<ApArticle> sourceAsObjectList = searchResult.getSourceAsObjectList(ApArticle.class);
            List<ApArticle> resultList = new ArrayList<>();
            for (ApArticle apArticle : sourceAsObjectList) {
                apArticle = articleMapper.selectArticleById(Long.valueOf(apArticle.getId()));
                if(apArticle==null){
                    continue;
                }
                resultList.add(apArticle);
            }
            return RespResult.okResult(resultList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return RespResult.errorResult(ErrorCodeEnum.PARAM_INVALID);
    }
}
