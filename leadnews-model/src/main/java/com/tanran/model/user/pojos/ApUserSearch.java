package com.tanran.model.user.pojos;

import com.tanran.model.annotation.IdEncrypt;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ApUserSearch {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户ID
     */
    @IdEncrypt
    private Integer entryId;

    /**
     * 搜索词
     */
    private String keyword;

    /**
     * 当前状态0 无效 1有效
     */
    private Byte status;

    /**
     * 创建时间
     */
    private Date createdTime;

}