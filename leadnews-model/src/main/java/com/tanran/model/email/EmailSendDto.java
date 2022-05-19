package com.tanran.model.email;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/5/19 9:04
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailSendDto {
    /**
     * 邮件接收方，可多人
     */
    private List<String> tos;
    /**
     * 邮件主题
     */
    private String subject;
    /**
     * 邮件内容
     */
    private String content;

    public void setContentByFans(String authoerName,String title){
        this.content = "你好，你所关注的作者【"+authoerName+"】发布了新文章”"+title+"“，快前往查看吧！！";
    }

    public void setContentBySubscription(String channleName,String title){
        this.content = "你好，你所订阅的频道【"+channleName+"】更新了新文章”"+title+"“，快前往查看吧！！";
    }
}
