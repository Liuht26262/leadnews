package com.tanran.wemedia.service.imple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tanran.common.result.RespResult;
import com.tanran.model.common.constants.WmMediaConstans;
import com.tanran.model.common.dtos.PageResponseResult;
import com.tanran.model.common.enums.ErrorCodeEnum;
import com.tanran.model.mappers.wemedia.WmMaterialMapper;
import com.tanran.model.mappers.wemedia.WmNewsMapper;
import com.tanran.model.mappers.wemedia.WmNewsMaterialMapper;
import com.tanran.model.media.dtos.WmNewsDto;
import com.tanran.model.media.dtos.WmNewsPageReqDto;
import com.tanran.model.media.pojos.WmMaterial;
import com.tanran.model.media.pojos.WmNews;
import com.tanran.model.media.pojos.WmUser;
import com.tanran.utils.threadlocal.WmThreadLocalUtils;
import com.tanran.wemedia.service.WmNewsService;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/12 16:42
 */
@Service
@Slf4j
public class WmNewsServiceImpl implements WmNewsService {
    @Autowired
    private WmNewsMapper wmNewsMapper;
    @Autowired
    private WmMaterialMapper wmMaterialMapper;
    @Autowired
    private WmNewsMaterialMapper wmNewsMaterialMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Value("${FILE_SERVER_URL}")
    private String fileServerUrl;

    /**- 如果用户传递参数为空或文章内容为空返回PARAM_REQUIRE错误
     - 如果用户本次为修改操作那么先删除数据库中的信息
     - 保存或修改文章的数据
     - 保存内容中的图片和当前文章的关系
     - 保存封面图片和当前文章的关系
     - 流程处理完成返回处理结果
     */

    @Override
    public RespResult saveNews(WmNewsDto dto,Short type) {

        if(Objects.isNull(dto) || StringUtils.isEmpty(dto.getContent())){
            return RespResult.errorResult(ErrorCodeEnum.PARAM_REQUIRE);
        }

        WmUser user = WmThreadLocalUtils.getUser();
        if(!Objects.isNull(user)){
            wmNewsMaterialMapper.deleteByPrimaryKey(user.getId());
        }

        //解析文章类容，进行图文素材关联
        String content = dto.getContent();

        //Map<图片排序号， dfs文件id>
        Map<String, Object> materials;
        try {
            List<Map> list = objectMapper.readValue(content, List.class);
            //抽取信息
            Map<String, Object> extractInfo = extractUrlInfo(list);
            materials = (Map<String, Object>) extractInfo.get("materials");
            //文章图片总数量
            int countImageNum = (int) extractInfo.get("countImageNum");
            //保存发布文章信息
            WmNews wmNews = new WmNews();
            //复制类信息
            BeanUtils.copyProperties(dto, wmNews);

            if (dto.getType().equals(WmMediaConstans.WM_NEWS_TYPE_AUTO)){
                saveWmNews(wmNews, countImageNum, type);
            }else{
                saveWmNews(wmNews, dto.getType(), type);
            }
            //保存内容中的图片和当前文章的关系
            if (materials.keySet().size() != 0) {
                RespResult respResult = saveRelativeInfoForContent(materials, wmNews.getId());
                if (respResult != null) {
                    return respResult;
                }
            }
            //封面图片关联
            RespResult respResult = coverImagesRelation(dto, materials, wmNews, countImageNum);
            if (respResult != null) {
                return respResult;
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("parse content error, param content :{}", content);
            return RespResult.errorResult(ErrorCodeEnum.PARAM_INVALID);
        }
        return RespResult.okResult(ErrorCodeEnum.SUCCESS);
    }


    /**
     * 封面图片关联
     * @param dto
     * @param materials
     * @param wmNews
     * @param countImageNum
     * @return
     */
    private RespResult coverImagesRelation(WmNewsDto dto, Map<String, Object> materials, WmNews wmNews, int countImageNum) {
        if (dto.getImages() == null){
            return RespResult.errorResult(ErrorCodeEnum.PARAM_INVALID);
        }
        List<String> images = dto.getImages();
        if (!WmMediaConstans.WM_NEWS_TYPE_AUTO.equals(dto.getType()) && dto.getType() != images.size()) {
            return RespResult.errorResult(ErrorCodeEnum.PARAM_INVALID, "图文模式不匹配");
        }
        //如果是自动匹配封面
        if (WmMediaConstans.WM_NEWS_TYPE_AUTO.equals(dto.getType())) {
            images = new ArrayList<>();
            if (countImageNum == WmMediaConstans.WM_NEWS_SINGLE_IMAGE) {
                for (Object value : materials.values()) {
                    images.add(String.valueOf(value));
                    break;
                }
            }
            if (countImageNum >= WmMediaConstans.WM_NEWS_MANY_IMAGE) {
                for (int i = 0; i < WmMediaConstans.WM_NEWS_MANY_IMAGE; i++) {
                    images.add((String) materials.get(String.valueOf(i)));
                }
            }
            if (images.size() != 0) {
                RespResult responseResult = saveRelativeInfoForCover(images, wmNews.getId());
                if (responseResult != null) {
                    return responseResult;
                }
            }
        } else if(images != null && images.size() != 0) {
            RespResult responseResult = saveRelativeInfoForCover(images, wmNews.getId());
            if (responseResult != null) {
                return responseResult;
            }
        }
        //更新images字段
        if (images != null) {
            wmNews.setImages(
                StringUtils.join(
                    images.stream().map(s -> s.replace(fileServerUrl, "")).collect(Collectors.toList()),
                    WmMediaConstans.WM_NEWS_IMAGES_SWPARATOR
                )
            );
            wmNewsMapper.updateByPrimaryKey(wmNews);
        }
        return null;
    }

    /**
     * 提取信息
     * @param list
     * @return
     */
    private Map<String, Object> extractUrlInfo(List<Map> list) {
        Map<String, Object> res = new HashMap<>();
        Map<String, Object> materials = new HashMap<>();
        int order = 0;
        int countImageNum = 0;
        //收集文章中引用的资源服务器的图片url以及排序
        for (Map map : list) {
            order++;
            if (WmMediaConstans.WM_NEWS_TYPE_IMAGE.equals(map.get("type"))) {
                countImageNum++;
                String imgUrl = String.valueOf(map.get("value"));
                if(imgUrl.startsWith(fileServerUrl)) {
                    materials.put(String.valueOf(order), imgUrl.replace(fileServerUrl, ""));
                }
            }
        }
        res.put("materials", materials);
        res.put("countImageNum", countImageNum);
        return res;
    }

    /**
     * 保存关联信息到数据库
     * @param materials
     * @param newsId
     */
    private RespResult saveRelativeInfo(Map<String, Object> materials, Integer newsId, Short type) {
        WmUser user = WmThreadLocalUtils.getUser();
        //收集数据库中的素材信息
        List<WmMaterial> dbMaterialInfos = wmMaterialMapper.findMaterialByUidAndimgUrls(user.getId().longValue(), materials.values());
        if (dbMaterialInfos != null && dbMaterialInfos.size() != 0) {
            Map<String, Object> urlIdMap = dbMaterialInfos.stream().collect(
                Collectors.toMap(WmMaterial::getUrl, WmMaterial::getId));
            for (String key : materials.keySet()) {
                String fileId = String.valueOf(urlIdMap.get(materials.get(key)));
                if ("null".equals(fileId)) {
                    return RespResult.errorResult(ErrorCodeEnum.PARAM_INVALID, "应用图片失效");
                }
                materials.put(key, String.valueOf(fileId));
            }
            //存储关系数据到数据库
            wmNewsMaterialMapper.saveRelationsByContent(materials, newsId, type);
        }
        return null;
    }


    /**
     * 保存图片关系为封面
     * @param images
     * @param newsId
     */
    private RespResult saveRelativeInfoForCover(List<String> images, Integer newsId) {
        Map<String, Object> materials = new HashMap<>();
        for (int i = 0; i < images.size(); i++) {
            String s = images.get(i);
            s = s.replace(fileServerUrl, "");
            materials.put(String.valueOf(i), s);
        }
        return saveRelativeInfo(materials, newsId, WmMediaConstans.WM_IMAGE_REFERENCE);
    }


    /**
     * 保存图片关系为内容
     * @param materials
     * @param newsId
     */
    private RespResult saveRelativeInfoForContent(Map<String, Object> materials, Integer newsId) {
        return saveRelativeInfo(materials, newsId, WmMediaConstans.WM_CONTENT_REFERENCE);
    }

    /**
     * 保存/修改发布文章信息
     * @param wmNews
     * @param countImageNum
     * @param type
     */
    private void saveWmNews(WmNews wmNews, int countImageNum, Short type) {
        WmUser user = WmThreadLocalUtils.getUser();
        //保存提交文章数据
        if (countImageNum == WmMediaConstans.WM_NEWS_SINGLE_IMAGE) {
            wmNews.setType(WmMediaConstans.WM_NEWS_SINGLE_IMAGE);
        } else if (countImageNum >= WmMediaConstans.WM_NEWS_MANY_IMAGE) {
            wmNews.setType(WmMediaConstans.WM_NEWS_MANY_IMAGE);
        } else {
            wmNews.setType(WmMediaConstans.WM_NEWS_NONE_IMAGE);
        }
        wmNews.setStatus(type);
        wmNews.setUserId(user.getId().longValue());
        wmNews.setCreatedTime(new Date());
        wmNews.setSubmitedTime(new Date());
        // wmNews.setEnable((short)1);
        if (wmNews.getId() == null) {
            wmNewsMapper.insertNewsForEdit(wmNews);
        }else {
            wmNewsMapper.updateByPrimaryKey(wmNews);
        }

    }

    /**
     * 加载新闻列表
     *
     * @return*/
    @Override
    public PageResponseResult selectAllNews(WmNewsPageReqDto dto) {
        if(Objects.isNull(dto)){
            return (PageResponseResult) PageResponseResult.errorResult(ErrorCodeEnum.PARAM_INVALID);
        }
        dto.checkParam();

        WmUser user = WmThreadLocalUtils.getUser();

        List <WmNews> wmNewsList = wmNewsMapper.selectAllNews(dto,user.getId().longValue());
        int total = wmNewsMapper.countSelectBySelective(dto, user.getId().longValue());
        PageResponseResult result = new PageResponseResult(dto.getPage(), dto.getSize(), total);
        result.setData(wmNewsList);
        result.setHost(fileServerUrl);

        return result;
    }

    /**
     * 删除新闻
     * @param dto
     * @return
     */
    @Override
    public RespResult deleteNews(WmNewsDto dto) {
        if(Objects.isNull(dto)||dto.getId()==null){
            return RespResult.errorResult(ErrorCodeEnum.PARAM_INVALID);
        }
        WmNews wmNews = wmNewsMapper.selectByPrimaryKey(dto.getId());
        if(Objects.isNull(wmNews)){
            RespResult.errorResult(ErrorCodeEnum.AP_USER_DATA_NOT_EXIST);
        }
        //判断是否已经通过审核
        if(WmMediaConstans.WM_NEWS_AUTHED_STATUS.equals(wmNews.getStatus())||WmMediaConstans.WM_NEWS_AUTHED_STATUS.equals(wmNews.getStatus())){
            return RespResult.errorResult(ErrorCodeEnum.PARAM_INVALID,"文章已经审核通过，不可删除");
        }

        //删除关联表信息
        wmNewsMaterialMapper.deleteByUserId(dto.getId());

        int i = wmNewsMapper.deleteByPrimaryKey(dto.getId());

        return RespResult.okResult(i);
    }

    @Override
    public WmNews selectNewById(Integer id) {
        return wmNewsMapper.selectByPrimaryKey(id);
    }

}
