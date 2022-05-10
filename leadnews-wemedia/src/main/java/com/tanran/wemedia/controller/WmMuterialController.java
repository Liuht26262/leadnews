package com.tanran.wemedia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tanran.api.wmediaApi.WmMuterialApi;
import com.tanran.common.result.RespResult;
import com.tanran.model.media.dtos.CollectMaterDto;
import com.tanran.model.media.dtos.WmMaterialDto;
import com.tanran.model.media.dtos.WmMaterialListDto;
import com.tanran.wemedia.service.MaterialService;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/14 15:38
 */
@RestController
@RequestMapping("/api/v1/media/material")
public class WmMuterialController implements WmMuterialApi {
    @Autowired
    private MaterialService materialService;

    @Override
    @PostMapping("/upload_picture")
    public RespResult uploadPicture(MultipartFile file) {
        System.out.println(file);
        return materialService.uploadPicture(file);
    }

    @Override
    @PostMapping("/del_picture")
    public RespResult deletePicture(@RequestBody WmMaterialDto dto) {
        return materialService.deletePicture(dto);
    }

    /**此接口用于加载自媒体人的图文素材，和用于图片素材选择框。*/
    @Override
    @PostMapping("/list")
    public RespResult loadAllMaterial(@RequestBody WmMaterialListDto ids) {
        return materialService.loadAllMaterIal(ids);
    }

    @Override
    @PostMapping("/collect")
    public RespResult collectMaterial(@RequestBody CollectMaterDto dto) {
        return materialService.collectMaterial(dto);
    }

}
