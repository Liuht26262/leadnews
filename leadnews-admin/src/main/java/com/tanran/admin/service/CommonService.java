package com.tanran.admin.service;


import com.tanran.common.result.RespResult;
import com.tanran.model.admin.dtos.CommonDto;

/**
 *
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/17 14:15
 */


public interface CommonService {

    /**
     * 列表查询-->无条件查询，无条件统计    有条件的查询 有条件的统计
     * @param dto
     * @return
     */
    public RespResult list(CommonDto dto);

    /**
     * 通过dto中的model来判断，选择使用新增或修改
     * @param dto
     * @return
     */
    public RespResult update(CommonDto dto);

    /**
     * 通用的删除
     * @param dto
     * @return
     */
    public RespResult delete(CommonDto dto);
}
