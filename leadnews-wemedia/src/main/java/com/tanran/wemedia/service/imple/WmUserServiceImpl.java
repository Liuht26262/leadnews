package com.tanran.wemedia.service.imple;

import java.util.HashMap;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tanran.common.result.RespResult;
import com.tanran.model.common.enums.ErrorCodeEnum;
import com.tanran.model.mappers.wemedia.WmUserMapper;
import com.tanran.model.media.pojos.WmUser;
import com.tanran.utils.jwt.AppJwtUtil;
import com.tanran.utils.threadlocal.WmThreadLocalUtils;
import com.tanran.wemedia.service.WmUserService;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/4/12 14:36
 */
@Service
public class WmUserServiceImpl implements WmUserService {
    @Autowired
    private WmUserMapper wmUserMapper;

    @Override
    public RespResult LoginWmUser(WmUser user) {
        HashMap<Object, Object> loginMap = new HashMap<>();
        //校验参数
        if(StringUtils.isEmpty(user.getName())&&StringUtils.isEmpty(user.getPassword())){
            return RespResult.errorResult(ErrorCodeEnum.PARAM_REQUIRE,"账号及密码不能为空");
        }

        WmUser wmUser = wmUserMapper.selectByName(user.getName(),user.getRoleType());
        System.out.println("******************************************************");
        System.out.println("用户信息:"+wmUser);
        System.out.println("******************************************************");
        if (!Objects.isNull(wmUser)){
           if(user.getPassword().equals(wmUser.getPassword())){
               System.out.println("已设定用户");
               //登陆成功后将密码制空返回，保证安全
               wmUser.setPassword("");
               wmUser.setSalt("");
               loginMap.put("token", AppJwtUtil.getToken(wmUser));
               loginMap.put("user",wmUser);
               return RespResult.okResult(loginMap);
           }else{
               return RespResult.errorResult(ErrorCodeEnum.LOGIN_PASSWORD_ERROR);
           }
        }
        return RespResult.errorResult(ErrorCodeEnum.USER_NOT_EXIT);
    }

    @Override
    public RespResult getUserFile() {
        WmUser user = WmThreadLocalUtils.getUser();
        WmUser wmUser = wmUserMapper.selectByPrimaryKey(user.getId());
        return RespResult.okResult(wmUser);
    }
}
