package com.tanran.model.article.dtos;

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
public class ArticleRequestDto {

    private Integer userId;
    private Integer channelId;
    private String timestamp;
    /**
     * 设置旧的时间戳，用来更新最新文章
     * */
    private String oldTimestamp;
    private Integer with_top;

    protected Integer size;
    protected Integer page;
    protected Integer total;


    public void checkParam() {
        if (this.page == null || this.page < 0) {
            setPage(1);
        }
        if (this.size == null || this.size < 0 || this.size > 100) {
            setSize(10);
        }
    }

}
