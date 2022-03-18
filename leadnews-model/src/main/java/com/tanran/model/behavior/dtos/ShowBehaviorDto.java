package com.tanran.model.behavior.dtos;


import com.tanran.model.annotation.IdEncrypt;
import com.tanran.model.article.pojos.ApArticle;

import lombok.Data;

import java.util.List;

@Data
public class ShowBehaviorDto {

    // 设备ID
    @IdEncrypt
    Integer equipmentId;
    List<ApArticle> articleIds;

}
