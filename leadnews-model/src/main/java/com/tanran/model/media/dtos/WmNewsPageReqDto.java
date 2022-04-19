package com.tanran.model.media.dtos;

import com.tanran.model.annotation.IdEncrypt;
import com.tanran.model.common.dtos.PageRequestDto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class WmNewsPageReqDto extends PageRequestDto {

    private Short status;

    private Date beginPubdate;
    private Date endPubdate;
    @IdEncrypt
    private Integer channelId;
    private String keyWord;
}
