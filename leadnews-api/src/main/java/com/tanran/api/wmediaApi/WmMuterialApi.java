package com.tanran.api.wmediaApi;

import org.springframework.web.multipart.MultipartFile;

import com.tanran.common.result.RespResult;
import com.tanran.model.media.dtos.WmMaterialDto;
import com.tanran.model.media.dtos.WmMaterialListDto;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 图片操作Api
 * @since 2022/4/14 15:36
 */
public interface WmMuterialApi {

    RespResult uploadPicture(MultipartFile file);

    RespResult deletePicture(WmMaterialDto dto);

    RespResult loadAllMaterial(WmMaterialListDto ids);
}
