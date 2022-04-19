package com.tanran.wemedia.service;

import org.springframework.web.multipart.MultipartFile;

import com.tanran.common.result.RespResult;
import com.tanran.model.media.dtos.WmMaterialDto;
import com.tanran.model.media.dtos.WmMaterialListDto;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/14 15:02
 */
public interface MaterialService {
    RespResult uploadPicture(MultipartFile multipartFile);

    RespResult deletePicture(WmMaterialDto dto);

    RespResult loadAllMaterIal(WmMaterialListDto ids);
}
