package com.tanran.model.article.dtos;

import java.util.List;

import lombok.Data;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/25 17:42
 */
@Data
public class ArticleRespDto {

    protected Integer size;
    protected Integer page;
    protected Integer total;
    public List<Results> results;

    public ArticleRespDto(){};

    public ArticleRespDto(Integer size, Integer page, Integer total, List results) {
        this.size = size;
        this.page = page;
        this.total = total;
        this.results = results;
    }




    public void checkParam() {
        if (this.page == null || this.page < 0) {
            setPage(1);
        }
        if (this.size == null || this.size < 0 || this.size > 100) {
            setSize(10);
        }
    }

    public static class Results{

        public Integer articleId;
        public String title;
        public Integer authorId;
        public String authorName;
        public Integer commCount;
        //发布时间
        public String pubdate;
        //封面
        public Cover cover;

        public Results(Integer articleId, String title, Integer authorId, String authorName, Integer commCount,
            String pubdate, Cover cover, Integer likeCount, Integer collectCount) {
            this.articleId = articleId;
            this.title = title;
            this.authorId = authorId;
            this.authorName = authorName;
            this.commCount = commCount;
            this.pubdate = pubdate;
            this.cover = cover;
            this.likeCount = likeCount;
            this.collectCount = collectCount;
        }

        private Integer likeCount;
        private Integer collectCount;




        public Results() {

        }
    }

    public static class  Cover{
        public Integer type;
        public String[] images;

        public Cover(Integer type,String[] images){
            this.type = type;
            this.images = images;
        }

        public void setType(Integer type){
            this.type = type;
        }

    }
}
