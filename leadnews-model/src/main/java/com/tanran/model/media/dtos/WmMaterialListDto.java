package com.tanran.model.media.dtos;

import com.tanran.model.common.dtos.PageRequestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WmMaterialListDto extends PageRequestDto {
    Short isCollected; //1 查询收藏的
}
