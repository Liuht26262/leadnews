package com.tanran.model.article.dtos;

import java.util.List;

import lombok.Data;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 评论响应Dto
 * @since 2022/4/27 22:52
 */
@Data
public class CommentsRespDto {
    private Integer total;
    /**
     *所有评论或回复的最后一个id（截止offset值，小于此值的offset可以不用发送请求获取评论数据，已经没有数据），若无评论或回复数据，则值为NULL
     * */
    private Integer endId;
    /**
     *本次返回结果的最后一个评论id，作为请求下一页数据的offset参数，若本次无具体数据，则值为NULL
     * */
    private Integer lastId;
    /**
     * 评论或回复的内容
     * */
    public List<Result> results;

    public static class Result{
        public Integer commentId;
        public Integer authorId;
        public String authorName;
        public String authorPhoto;
        public Integer likeCount;
        public Integer replyCount;
        public String pubdate;
        public String content;
        public Integer isTop;
        public boolean isLike;

        public Result(){};
        public Result(Integer commentId, Integer authorId, String authorName, String authorPhoto, Integer likeCount,
            Integer replyCount, String pubdate, String content, Integer isTop, boolean isLike) {
            this.commentId = commentId;
            this.authorId = authorId;
            this.authorName = authorName;
            this.authorPhoto = authorPhoto;
            this.likeCount = likeCount;
            this.replyCount = replyCount;
            this.pubdate = pubdate;
            this.content = content;
            this.isTop = isTop;
            this.isLike = isLike;
        }

        public Integer getCommentId() {
            return commentId;
        }

        public void setCommentId(Integer commentId) {
            this.commentId = commentId;
        }

        public Integer getAuthorId() {
            return authorId;
        }

        public void setAuthorId(Integer authorId) {
            this.authorId = authorId;
        }

        public String getAuthorName() {
            return authorName;
        }

        public void setAuthorName(String authorName) {
            this.authorName = authorName;
        }

        public String getAuthorPhoto() {
            return authorPhoto;
        }

        public void setAuthorPhoto(String authorPhoto) {
            this.authorPhoto = authorPhoto;
        }

        public Integer getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(Integer likeCount) {
            this.likeCount = likeCount;
        }

        public Integer getReplyCount() {
            return replyCount;
        }

        public void setReplyCount(Integer replyCount) {
            this.replyCount = replyCount;
        }

        public String getPubdate() {
            return pubdate;
        }

        public void setPubdate(String pubdate) {
            this.pubdate = pubdate;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Integer getIsTop() {
            return isTop;
        }

        public void setIsTop(Integer isTop) {
            this.isTop = isTop;
        }

        public boolean isLike() {
            return isLike;
        }

        public void setLike(boolean like) {
            isLike = like;
        }



    }
}
