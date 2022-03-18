package com.tanran.model.user.dtos;

import com.tanran.model.common.dtos.PageRequestDto;

import lombok.Data;

@Data
public class UserFansPageReqDto extends PageRequestDto {
    private String fansName;
    private Short level;
}
