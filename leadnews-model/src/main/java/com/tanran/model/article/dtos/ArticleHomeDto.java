package com.tanran.model.article.dtos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 3.0.x 2022/3/18 15:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleHomeDto {
    //市
    Integer provinceId;
    // 市区
    Integer cityId;
    // 区县
    Integer countyId;
    // 最大时间
    Date maxBehotTime;
    // 最小时间
    Date minBehotTime;
    // 分页size
    Integer size;
    // 数据范围，比如频道ID
    String tag;
}
