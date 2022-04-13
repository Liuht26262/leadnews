package com.tanran.model.media.pojos;

import lombok.Data;

import java.util.Date;

@Data
public class WmUser {
    /**
     * 主键
     */
    private Integer id;

    /**
     *
     */
    private Integer apUserId;

    /**
     *
     */
    private Integer apAuthorId;

    /**
     * 登录用户名
     */
    private String name;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String image;

    /**
     * 归属地
     */
    private String location;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 状态
     0 暂时不可用
     1 永久不可用
     9 正常可用
     */
    private Byte status;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 账号类型
     0 个人
     1 企业
     2 子账号
     */
    private Boolean type;

    /**
     * 运营评分
     */
    private Byte score;

    /**
     * 最后一次登录时间
     */
    private Date loginTime;

    /**
     * 创建时间
     */
    private Date createdTime;
}