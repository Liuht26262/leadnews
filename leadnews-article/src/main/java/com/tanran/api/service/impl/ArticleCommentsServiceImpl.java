package com.tanran.api.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.tanran.api.service.ArticleCommentsService;
import com.tanran.common.result.RespResult;
import com.tanran.model.article.dtos.CommentAddDto;
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
            if(CollectionUtils.isEmpty(apCommentRepays)){
                return RespResult.okResult(ErrorCodeEnum.SUCCESS);
            }
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
            respDto.setEndId(apCommentRepayMapper.selectLastComments(dto.getSource()).getId());
            respDto.setTotal(results.size());
        }

        return RespResult.okResult(respDto);
    }

    @Override
    public RespResult addComments(ApUser user,CommentAddDto dto) throws ParseException {
        if(Objects.isNull(dto)){
            return RespResult.errorResult(ErrorCodeEnum.SERVER_ERROR);
        }

        CommentsRespDto.Result result = new CommentsRespDto.Result();

        if(dto.getArticleId() != null){
            /**说明这是评论的回复
             * 先查询评论表获取所评论的作者信息
             * */
            Date time = new Date(System.currentTimeMillis());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            String format = simpleDateFormat.format(time);
            Date date = simpleDateFormat.parse(format);
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


            result.setAuthorName(repay.getAuthorName());
            result.setAuthorId(repay.getAuthorId());
            result.setIsTop(1);
            result.setAuthorPhoto(repay.getImage());
            result.setContent(repay.getContent());
            result.setCommentId(repay.getId());
            result.setPubdate(repay.getCreatedTime().toString());
            Integer count = apCommentRepayMapper.countRepay(repay.getAuthorId());
            result.setLikeCount(count);
            result.setReplyCount(0);

            // respDto.setArticleId(repay.getArticleId());
            // respDto.setTarget(dto.getTarget());
            // respDto.setCommentId(repay.getId());
            // respDto.setNewComment(repay.getContent());

        }else {
            /**如果articleId没有值就说明是文章的id*/

            Date time = new Date(System.currentTimeMillis());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            String format = simpleDateFormat.format(time);
            Date date = simpleDateFormat.parse(format);

            ApComment apComment = new ApComment();
            apComment.setArticleId(dto.getTarget());
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
            apComment.setFlag((byte)0);
            apComment.setUpdatedTime(date);


            apCommentMapper.insert(apComment);
            ApComment comment = apCommentMapper.selectCommentRepay(user.getId().intValue(),date);

            if(Objects.isNull(comment)){
                log.error("**************没有获取到评论数据******************");
                return null;
            }


            result.setAuthorName(comment.getAuthorName());
            result.setAuthorId(comment.getAuthorId());
            result.setIsTop(comment.getFlag() == 3 ? 0 : 1);
            result.setAuthorPhoto(comment.getImage());
            result.setContent(comment.getContent());
            result.setCommentId(comment.getId());
            result.setPubdate(comment.getCreatedTime().toString());
            Integer count = apCommentRepayMapper.countRepay(comment.getAuthorId());
            result.setLikeCount(count);
            result.setReplyCount(0);
        }
        return RespResult.okResult(result);
    }
}
