package com.tanran.wemedia.service.imple;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tanran.common.result.RespResult;
import com.tanran.model.common.enums.ErrorCodeEnum;
import com.tanran.model.mappers.wemedia.WmMaterialMapper;
import com.tanran.model.mappers.wemedia.WmNewsMapper;
import com.tanran.model.mappers.wemedia.WmNewsMaterialMapper;
import com.tanran.model.media.dtos.WmNewsDto;
import com.tanran.model.media.pojos.WmUser;
import com.tanran.utils.threadlocal.WmThreadLocalUtils;
import com.tanran.wemedia.service.WmNewsService;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/12 16:42
 */
@Service
public class WmNewsServiceImpl implements WmNewsService {
    @Autowired
    private WmNewsMapper wmNewsMapper;
    @Autowired
    private WmMaterialMapper wmMaterialMapper;
    @Autowired
    private WmNewsMaterialMapper wmNewsMaterialMapper;

    /**- 如果用户传递参数为空或文章内容为空返回PARAM_REQUIRE错误
     - 如果用户本次为修改操作那么先删除数据库中的信息
     - 保存或修改文章的数据
     - 保存内容中的图片和当前文章的关系
     - 保存封面图片和当前文章的关系
     - 流程处理完成返回处理结果
     */

    @Override
    public RespResult saveNews(WmNewsDto dto) {

        if(Objects.isNull(dto) || StringUtils.isEmpty(dto.getContent())){
            return RespResult.errorResult(ErrorCodeEnum.PARAM_REQUIRE);
        }


        // WmUser user = WmThreadLocalUtils.getUser();
        // if(!Objects.isNull(user)){
        //     wmNewsMaterialMapper.deleteByPrimaryKey(user.getId());
        // }
        return null;
    }
}
