package com.tanran.model.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/5/5 10:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChannelReqDto {
    private Integer id;
    private String name;
    private Integer userId;
    private Integer seq;
}
