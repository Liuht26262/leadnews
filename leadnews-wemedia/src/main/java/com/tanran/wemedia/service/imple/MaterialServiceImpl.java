package com.tanran.wemedia.service.imple;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tanran.common.fastdfs.FastDfsClient;
import com.tanran.common.result.RespResult;
import com.tanran.model.common.enums.ErrorCodeEnum;
import com.tanran.model.mappers.wemedia.WmMaterialMapper;
import com.tanran.model.mappers.wemedia.WmNewsMaterialMapper;
import com.tanran.model.media.dtos.CollectMaterDto;
import com.tanran.model.media.dtos.WmMaterialDto;
import com.tanran.model.media.dtos.WmMaterialListDto;
import com.tanran.model.media.pojos.WmMaterial;
import com.tanran.model.media.pojos.WmNewsMaterial;
import com.tanran.model.media.pojos.WmUser;
import com.tanran.utils.threadlocal.WmThreadLocalUtils;
import com.tanran.wemedia.service.MaterialService;

import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 图片上传业务实现类
 * @since 2022/4/14 15:04
 */
@Service
@SuppressWarnings("all")
@Slf4j
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private FastDfsClient fastDfsClient;
    @Autowired
    private WmMaterialMapper wmMaterialMapper;
    @Autowired
    private WmNewsMaterialMapper wmNewsMaterialMapper;

    private static String pictureUrlServer = "http://47.243.173.194:1234/";

    /**
     * - 判断入参multipartFile是否合法，不合法则返回PARAM_INVALID错误
     * - 判断入参multipartFile是否有合法的扩展名，不合法则返回PARAM_INVALID错误
     * - 上传图片到FastDFS服务器
     * - 上传图片到服务器失败
     * - 上传图片流程完成， 返回信息给前端*/
    @Override
    public RespResult uploadPicture(MultipartFile multipartFile) {
        //获取当前用户信息
        WmUser user = WmThreadLocalUtils.getUser();
        System.out.println("*********************");
        System.out.println("上传图片时的用户数据为"+user);
        System.out.println("*********************");

        if(Objects.isNull(multipartFile)){
            return RespResult.errorResult(ErrorCodeEnum.PARAM_INVALID);
        }
        String fileName = multipartFile.getOriginalFilename();
        //获取图片格式
        String type = fileName.substring(fileName.lastIndexOf(".")+1);
        if(!type.matches("(gif|png|jpg|jpeg)")) {
            return
                RespResult.errorResult(ErrorCodeEnum.PARAM_IMAGE_FORMAT_ERROR);
        }

        String url = null;

        try {
            url = fastDfsClient.uploadFile(multipartFile.getBytes(), type);
        } catch (Exception e) {
            e.printStackTrace();
        }

        WmMaterial wm = WmMaterial.builder()
            .userId(user.getId())
            .type((short) 0)
            .isCollection((short) 0)
            .url(url)
            .createdTime(new Date())
            .build();

        wmMaterialMapper.insert(wm);

        wm.setUrl(pictureUrlServer+wm.getUrl());

        return RespResult.okResult(wm);
    }

    @Override
    public RespResult deletePicture(WmMaterialDto dto) {
        System.out.println("*************");
        System.out.println(dto);
        System.out.println("*************");

        if(Objects.isNull(dto)){
            RespResult.errorResult(ErrorCodeEnum.PARAM_INVALID);
        }
        WmMaterial wmMaterial = wmMaterialMapper.selectByPrimaryKey(dto.getId());

        if(Objects.isNull(wmMaterial)){
            return RespResult.errorResult(ErrorCodeEnum.DATA_EXIST);
        }

        //查询图片是否被引用,如果被引用则不可删除
        List<WmNewsMaterial> wmNewsMaterial = wmNewsMaterialMapper.selectWmByMaterialId(wmMaterial.getId());
        if(!Collections.isEmpty(wmNewsMaterial)){
            return RespResult.errorResult(ErrorCodeEnum.SERVER_ERROR,"图片已被文章引用，不可删除");
        }

        return RespResult.okResult(wmMaterialMapper.deleteByPrimaryKey(dto.getId()));

    }

    @Override
    public RespResult loadAllMaterIal(WmMaterialListDto ids) {
        System.out.println(ids);
        if(Objects.isNull(ids)){
            return RespResult.errorResult(ErrorCodeEnum.PARAM_INVALID);
        }
        ids.checkParam();
        //获取当前用户的信息
        WmUser user = WmThreadLocalUtils.getUser();
        List<WmMaterial> listByUidAndStatus = wmMaterialMapper.findListByUidAndStatus(ids, user.getId().longValue());
        if (Collections.isEmpty(listByUidAndStatus)) {
            return null;
        }

        //拼接图片地址
        listByUidAndStatus.stream().forEach(s -> s.setUrl(pictureUrlServer+s.getUrl()));

        Map<Object, Object> respMap = new HashMap<>();

        respMap.put("list",listByUidAndStatus);
        respMap.put("size",listByUidAndStatus.size());
        respMap.put("total",listByUidAndStatus.size());

        return RespResult.okResult(respMap);
    }

    @Override
    public RespResult collectMaterial(CollectMaterDto dto) {
        System.out.println(dto);
        if(Objects.isNull(dto)){
            return RespResult.errorResult(ErrorCodeEnum.PARAM_INVALID);
        }
        WmMaterial material = WmMaterial.builder()
            .id(dto.getId())
            .isCollection(dto.getIsCollection())
            .build();
        int result = wmMaterialMapper.updateByPrimaryKeySelective(material);
        return RespResult.okResult(result);
    }
}
