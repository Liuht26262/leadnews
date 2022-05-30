package com.tanran.model.article.pojos;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table ap_hot_words
 *
 * @mbg.generated do_not_delete_during_merge
 */
public class ApHotWords implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 热词
     */
    private String hotWords;

    /**
     * 0:热,1:荐,2:新,3:火,4:精,5:亮
     */
    private Byte type;

    /**
     * 热词日期
     */
    private String hotDate;

    /**
     * 创建时间
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
     * 获取 热词
     */
    public String getHotWords() {
        return hotWords;
    }

    /**
     * 设置 热词
     */
    public void setHotWords(String hotWords) {
        this.hotWords = hotWords;
    }

    /**
     * 获取 0:热,1:荐,2:新,3:火,4:精,5:亮
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置 0:热,1:荐,2:新,3:火,4:精,5:亮
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * 获取 热词日期
     */
    public String getHotDate() {
        return hotDate;
    }

    /**
     * 设置 热词日期
     */
    public void setHotDate(String hotDate) {
        this.hotDate = hotDate;
    }

    /**
     * 获取 创建时间
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * 设置 创建时间
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}