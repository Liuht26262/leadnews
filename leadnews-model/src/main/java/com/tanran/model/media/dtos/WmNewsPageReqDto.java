package com.tanran.model.media.dtos;

import com.tanran.model.annotation.IdEncrypt;
import com.tanran.model.common.dtos.PageRequestDto;

import lombok.Data;

import java.util.Date;

@Data
public class WmNewsPageReqDto extends PageRequestDto {

    private Short status;
    private Date beginPubdate;
    private Date endPubdate;
    @IdEncrypt
    private Integer channelId;
    private String keyWord;
}
