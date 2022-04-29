package login.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tanran.common.result.RespResult;
import com.tanran.model.common.dtos.ResponseResult;
import com.tanran.model.common.enums.AppHttpCodeEnum;
import com.tanran.model.mappers.app.ApUserMapper;
import com.tanran.model.user.pojos.ApUser;
import com.tanran.utils.jwt.AppJwtUtil;

import login.service.ApUserLoginService;

@Service
@SuppressWarnings("all")
public class ApUserLoginServiceImpl implements ApUserLoginService {

    @Autowired
    private ApUserMapper apUserMapper;

    @Override
    public RespResult loginAuth(ApUser user) {
        //验证参数
        if(StringUtils.isEmpty(user.getPhone())|| StringUtils.isEmpty(user.getPassword())){
            return RespResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        ApUser apUser = apUserMapper.selectUserByPhone(user.getPhone());
        if(apUser==null){
            return RespResult.errorResult(AppHttpCodeEnum.AP_USER_DATA_NOT_EXIST);
        }
        if(!user.getPassword().equals(apUser.getPassword())){
            return RespResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
        }
        apUser.setPassword("");
        Map<String,Object> map = new HashMap<>();
        map.put("token", AppJwtUtil.getToken(apUser));
        map.put("user",apUser);

        return RespResult.okResult(map);
    }
}
