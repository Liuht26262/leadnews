package com.tanran.admin.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tanran.admin.service.CommonService;
import com.tanran.common.result.RespResult;
import com.tanran.model.admin.dtos.CommonDto;

/**
 *
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/17 14:01
 */


@RestController
@RequestMapping("/api/v1/admin/common")
public class CommonController {

    @Autowired
    private CommonService commonService;

    @PostMapping("/list")
    public RespResult list(@RequestBody CommonDto dto){
        return commonService.list(dto);
    }

    @PostMapping("/update")
    public RespResult update(@RequestBody CommonDto dto){
        return commonService.update(dto);
    }

    @PostMapping("/delete")
    public RespResult delete(@RequestBody CommonDto dto){
        return commonService.delete(dto);
    }
}
