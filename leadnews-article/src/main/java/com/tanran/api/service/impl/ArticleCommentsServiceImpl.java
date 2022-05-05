package com.tanran.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.tanran.api.service.ArticleCommentsService;
import com.tanran.common.result.RespResult;
import com.tanran.model.article.dtos.CommentAddDto;
import com.tanran.model.article.dtos.CommentAddRespDto;
import com.tanran.model.article.dtos.CommentsReqDto;
import com.tanran.model.article.dtos.CommentsRespDto;
import com.tanran.model.article.pojos.ApComment;
import com.tanran.model.article.pojos.ApCommentRepay;
import com.tanran.model.common.enums.ErrorCodeEnum;
import com.tanran.model.mappers.app.ApCommentMapper;
import com.tanran.model.mappers.app.ApCommentRepayMapper;
import com.tanran.model.user.pojos.ApUser;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description 评论业务实现类
 * @since 2022/4/27 23:43
 */
@Service
@Slf4j
public class ArticleCommentsServiceImpl implements ArticleCommentsService {
    @Autowired
    private ApCommentMapper apCommentMapper;
    @Autowired
    private ApCommentRepayMapper apCommentRepayMapper;

    @Override
    public RespResult getComments(CommentsReqDto dto) {
        System.out.println("接收到的参数"+dto);
        if(Objects.isNull(dto)){
            return RespResult.errorResult(ErrorCodeEnum.PARAM_INVALID);
        }

        CommentsRespDto respDto = new CommentsRespDto();
        ArrayList<CommentsRespDto.Result> results = Lists.newArrayList();

        dto.checkParam();

        if("a".equals(dto.getType())){
            /**说明获取的是文章的评论*/
            List<ApComment> apComments = apCommentMapper.selectCommentByArticle(dto);
            if(Objects.nonNull(apComments)){
                apComments.stream().forEach(s -> {
                    CommentsRespDto.Result result = new CommentsRespDto.Result();
                    result.setAuthorName(s.getAuthorName());
                    result.setAuthorId(s.getAuthorId());
                    result.setIsTop(s.getFlag() == 3 ? 0 : 1);
                    result.setAuthorPhoto(s.getImage());
                    result.setContent(s.getContent());
                    result.setCommentId(s.getId());
                    result.setPubdate(s.getCreatedTime().toString());
                    Integer count = apCommentRepayMapper.countRepay(s.getAuthorId());
                    result.setLikeCount(count);
                    results.add(result);
                });

                respDto.setResults(results);
                respDto.setTotal(results.size());
                if(results.size() > 1){
                    respDto.setLastId(results.get(results.size()-1).getAuthorId());
                }
                if(Objects.nonNull(apCommentMapper.selectLastComments(dto.getSource()))){
                    respDto.setEndId(apCommentMapper.selectLastComments(dto.getSource()).getId());
                }

            }

        }else if("c".equals(dto.getType())){
            /**获取评论的回复*/
            List<ApCommentRepay> apCommentRepays = apCommentRepayMapper.selectByUser(dto);
            apCommentRepays.stream().forEach(s -> {
                CommentsRespDto.Result result = new CommentsRespDto.Result();
                result.setAuthorName(s.getAuthorName());
                result.setAuthorId(s.getAuthorId());
                result.setAuthorPhoto(s.getImage());
                result.setContent(s.getContent());
                result.setCommentId(s.getId());
                result.setPubdate(s.getCreatedTime()
                    .toString());
                Integer count = apCommentRepayMapper.countRepay(s.getAuthorId());
                result.setLikeCount(count);
                results.add(result);
            });
            respDto.setResults(results);
            respDto.setLastId(results.get(results.size()-1).getAuthorId());
            respDto.setEndId(apCommentMapper.selectLastComments(dto.getSource()).getId());
            respDto.setTotal(results.size());
        }

        return RespResult.okResult(respDto);
    }

    @Override
    public RespResult addComments(ApUser user,CommentAddDto dto) {
        if(Objects.isNull(dto)){
            return RespResult.errorResult(ErrorCodeEnum.SERVER_ERROR);
        }

        CommentAddRespDto respDto = new CommentAddRespDto();

        if(dto.getArticleId() == null){
            /**说明这是评论的回复
             * 先查询评论表获取所评论的作者信息
             * */
            Date date = new Date(System.currentTimeMillis());
            ApComment apComment = apCommentMapper.selectByPrimaryKey(dto.getTarget());
            ApCommentRepay apCommentRepay = new ApCommentRepay();
            apCommentRepay.setContent(dto.getContent());
            apCommentRepay.setCommentId(dto.getTarget());
            apCommentRepay.setCreatedTime(date);
            apCommentRepay.setArticleId(apComment.getArticleId());
            apCommentRepay.setIsLiking((byte) 0);
            apCommentRepay.setLikecount(0);
            apCommentRepay.setReplyCount(0);
            apCommentRepay.setUpdatedTime(date);
            apCommentRepay.setLatitude(null);
            apCommentRepay.setAuthorId(user.getId().intValue());
            apCommentRepay.setAuthorName(user.getName());
            apCommentRepay.setImage(user.getImage());
            apCommentRepayMapper.insert(apCommentRepay);
            ApCommentRepay repay = apCommentRepayMapper.selectCommentRepay(user.getId().intValue(),date);
            if(Objects.isNull(repay)){
                log.error("**************没有获取到评论数据******************");
                return null;
            }
            respDto.setArticleId(repay.getArticleId());
            respDto.setTarget(dto.getTarget());
            respDto.setCommentId(repay.getId());

        }else {
            /**如果articleId有值就说明是文章的id*/
            Date date = new Date(System.currentTimeMillis());
            ApComment apComment = new ApComment();
            apComment.setArticleId(dto.getArticleId());
            apComment.setContent(dto.getContent());
            apComment.setEntryId(user.getId().intValue());
            apComment.setCreatedTime(date);
            apComment.setReply(0);
            apComment.setReplyCount(0);
            apComment.setAuthorId(user.getId().intValue());
            apComment.setIsLiking((byte)0);
            apComment.setAuthorName(user.getName());
            apComment.setImage(user.getImage());
            apComment.setType("a");

            apCommentMapper.insert(apComment);
            ApComment comment = apCommentMapper.selectCommentRepay(user.getId().intValue(),date);

            if(Objects.isNull(comment)){
                log.error("**************没有获取到评论数据******************");
                return null;
            }
            respDto.setArticleId(comment.getArticleId());
            respDto.setTarget(dto.getTarget());
            respDto.setCommentId(comment.getId());
        }
        return RespResult.okResult(respDto);
    }
}
