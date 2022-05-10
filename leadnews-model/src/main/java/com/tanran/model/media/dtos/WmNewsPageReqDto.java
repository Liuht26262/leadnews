package com.tanran.model.media.dtos;

import com.tanran.model.annotation.IdEncrypt;
import com.tanran.model.common.dtos.PageRequestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WmNewsPageReqDto extends PageRequestDto {

    private Short status;

    private Date beginPubdate;
    private Date endPubdate;
    @IdEncrypt
    private Integer channelId;
    private String keyWord;
}
