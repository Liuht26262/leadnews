package com.tanran.model.user.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tanran.model.annotation.IdEncrypt;

import lombok.Data;

import java.util.Date;

@Data
public class ApUserFan {
    /**
     * 主键
     */
    private Long id;

    /**
     * 用户ID
     */
    @IdEncrypt
    private Integer userId;

    /**
     * 粉丝ID
     */
    @IdEncrypt
    private Long fansId;

    /**
     * 粉丝昵称
     */
    private String fansName;

    /**
     * 粉丝忠实度
     0 正常
     1 潜力股
     2 勇士
     3 铁杆
     4 老铁
     */
    private short level;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 是否可见我动态
     */
    private Boolean isDisplay;

    /**
     * 是否屏蔽私信
     */
    private Boolean isShieldLetter;

    /**
     * 是否屏蔽评论
     */
    private Boolean isShieldComment;

    /**
     *
     */
    @JsonIgnore
    private String burst;
}