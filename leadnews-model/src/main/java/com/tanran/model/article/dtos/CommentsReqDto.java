package com.tanran.model.article.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 评论请求数据
 * @since 2022/4/27 23:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentsReqDto {
    /**
     * 评论类型，a-对文章(article)的评论，c-对评论(comment)的回复
     * */
    private String type;
    /**
     *源id，文章id或评论id
     * */
    private Integer source;

    /**
     *获取评论数据的偏移量，值为评论id，表示从此id的数据向后取，不传表示从第一页开始读取数据
     * */
    private Integer page;

    /**
     *获取的评论数据个数，不传表示采用后端服务设定的默认每页数据量
     * */
    private Integer size;

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
