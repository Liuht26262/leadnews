package com.tanran.model.media.dtos;

import com.tanran.model.annotation.IdEncrypt;

import lombok.Data;

@Data
public class WmMaterialDto {

    @IdEncrypt
    private Integer id;

//    private String url;
}
