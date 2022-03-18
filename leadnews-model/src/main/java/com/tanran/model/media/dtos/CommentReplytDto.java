package com.tanran.model.media.dtos;

import com.tanran.model.annotation.IdEncrypt;

import lombok.Data;

@Data
public class CommentReplytDto {

    @IdEncrypt
    private Integer commentId;
    private String content;

}
