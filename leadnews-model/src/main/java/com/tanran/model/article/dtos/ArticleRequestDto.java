package com.tanran.model.article.dtos;

import com.tanran.model.common.dtos.PageRequestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 文章请求Dto
 * @since 2022/4/25 17:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleRequestDto extends PageRequestDto {

    private Integer userId;
    private Integer channelId;
    private String timestamp;
    /**
     * 设置旧的时间戳，用来更新最新文章
     * */
    private String oldTimestamp;
    private Integer with_top;

}
