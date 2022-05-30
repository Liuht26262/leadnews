package com.tanran.wemedia.service.imple;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tanran.common.result.RespResult;
import com.tanran.model.common.constants.WmMediaConstans;
import com.tanran.model.common.dtos.PageRequestDto;
import com.tanran.model.common.dtos.PageResponseResult;
import com.tanran.model.common.enums.ErrorCodeEnum;
import com.tanran.model.mappers.app.ApUserFanMapper;
import com.tanran.model.mappers.app.ApUserFollowMapper;
import com.tanran.model.mappers.app.ApUserMapper;
import com.tanran.model.mappers.wemedia.NewsStatisticsMapper;
import com.tanran.model.mappers.wemedia.WmUserMapper;
import com.tanran.model.media.dtos.FansReqDto;
import com.tanran.model.media.dtos.StatisticDto;
import com.tanran.model.media.pojos.WmNewsStatistics;
import com.tanran.model.media.pojos.WmUser;
import com.tanran.model.user.pojos.ApUser;
import com.tanran.model.user.pojos.ApUserFan;
import com.tanran.model.user.pojos.ApUserFollow;
import com.tanran.utils.threadlocal.WmThreadLocalUtils;
import com.tanran.wemedia.service.WmStatisticsService;

import io.jsonwebtoken.lang.Collections;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/15 17:02
 */
@Service
public class WmStatisticsServiceImpl implements WmStatisticsService {

    @Autowired
    private NewsStatisticsMapper newsStatisticsMapper;
    @Autowired
    private WmUserMapper wmUserMapper;
    @Autowired
    private ApUserFanMapper userFanMapper;
    @Autowired
    private ApUserFollowMapper apUserFollowMapper;
    @Autowired
    private ApUserMapper apUserMapper;
    /**
     * 图文统计
     * */
    @Override
    public RespResult newsStatistics(StatisticDto dto) {
        RespResult respResult = checkParam(dto);
        if("200".equals(respResult)){
            return respResult;
        }

        WmUser user = WmThreadLocalUtils.getUser();

        if(Objects.isNull(user)){
            return RespResult.errorResult(ErrorCodeEnum.NEED_LOGIN);
        }

        WmUser wmUser = wmUserMapper.selectByPrimaryKey(user.getId());

        WmNewsStatistics wmNewsStatistics = newsStatisticsMapper.selectNewStatistics(dto);
        if(Objects.isNull(wmNewsStatistics)){
            return RespResult.errorResult(ErrorCodeEnum.USER_NOT_EXIT);
        }

        return respResult.ok(wmNewsStatistics);
    }

    @Override
    public PageResponseResult fansStatistics(PageRequestDto dto) {


        WmUser user = WmThreadLocalUtils.getUser();

        WmUser wmUser = wmUserMapper.selectByPrimaryKey(user.getId());

        /**根据作者id来统计粉丝数据*/
        List<ApUserFan> apUserFansList = userFanMapper.selectUserFanById(wmUser.getApAuthorId());

        PageResponseResult result = new PageResponseResult();
        result.setData(apUserFansList);
        result.setTotal(apUserFansList.size());
        return result;
    }

    @Override
    public RespResult fansList(FansReqDto dto) {
        if(Objects.isNull(dto)){
            return RespResult.errorResult(ErrorCodeEnum.PARAM_INVALID);
        }
        dto.checkParam();
        WmUser wmUser = wmUserMapper.selectByPrimaryKey(dto.getId());
        List<ApUserFollow> userFollowList = apUserFollowMapper.selectUserFollowByAuthorId(wmUser.getApAuthorId());
        if(Collections.isEmpty(userFollowList)){
            return RespResult.OkResult(0,"该作者还没有粉丝");
        }
        List<Long> userIdList = userFollowList.stream()
            .map(s -> s.getUserId()
                .longValue())
            .collect(Collectors.toList());
        dto.setPage(dto.getPage()-1);
        List<ApUser> userList = apUserMapper.selectApUserByIds(userIdList,dto.getSize(),dto.getPage());
        return RespResult.okResult(userList);
    }

    @Override
    public RespResult cancelFollow(FansReqDto dto) {
        System.out.println(dto);
        if(Objects.isNull(dto)){
            return RespResult.errorResult(ErrorCodeEnum.PARAM_INVALID);
        }
        ApUserFollow apUserFollow = apUserFollowMapper.selectUserFollowByFollowId(dto.getUserId()
            .longValue(), dto.getId());
        if(Objects.nonNull(apUserFollow)){
            apUserFollowMapper.deleteUserFollow(dto.getUserId().longValue(),dto.getId());
        }
        return RespResult.OkResult(0,"成功");
    }

    @Override
    public RespResult loadFansInfo(Integer fansId) {
        ApUser apUser = apUserMapper.selectUserById(fansId.longValue());
        apUser.setPassword("");
        return RespResult.okResult(apUser);
    }

    /**
     * 校验请求参数
     * @param dto
     * @return
     */
    public RespResult checkParam(StatisticDto dto){
        if(Objects.isNull(dto)||dto.getType()==null){
            return RespResult.errorResult(ErrorCodeEnum.PARAM_INVALID);
        }

        if (WmMediaConstans.WM_NEWS_STATISTIC_CUR != dto.getType()&&((dto.getStime()==null)||(dto.getEtime()==null))){
            return RespResult.errorResult(ErrorCodeEnum.PARAM_INVALID);
        }

        return RespResult.okResult("200");
    }
}
