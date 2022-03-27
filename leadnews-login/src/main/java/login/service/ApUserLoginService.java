package login.service;

import com.tanran.common.result.RespResult;
import com.tanran.model.user.pojos.ApUser;

public interface ApUserLoginService {

    /**
     * 根据用户名和密码登录验证
     */
    RespResult loginAuth(ApUser user);
}
