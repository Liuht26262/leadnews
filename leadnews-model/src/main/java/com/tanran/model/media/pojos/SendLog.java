package com.tanran.model.media.pojos;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table send_log
 *
 * @mbg.generated do_not_delete_during_merge
 */
public class SendLog implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 信息详情
     */
    private String sendMessage;

    /**
     * 
     */
    private Date createdTime;

    /**
    
     */
    private static final long serialVersionUID = 1L;

    /**
     * 获取 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取 
     */
    public String getSendMessage() {
        return sendMessage;
    }

    /**
     * 设置 
     */
    public void setSendMessage(String sendMessage) {
        this.sendMessage = sendMessage;
    }

    /**
     * 获取 
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * 设置 
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}