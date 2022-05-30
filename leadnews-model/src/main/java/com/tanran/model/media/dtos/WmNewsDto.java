package com.tanran.model.media.dtos;

import java.util.Date;
import java.util.List;

import com.tanran.model.annotation.IdEncrypt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WmNewsDto {
    private Integer id;
    private String title;
    @IdEncrypt
    private Integer channelId;
    private String labels;
    private Date publishTime;
    private String content;
    private Short type;
    private Date submitedTime;
    private Short status;
    private String reason;
    private List<String> images;
    private String channleName;
    private String authorName;
}
