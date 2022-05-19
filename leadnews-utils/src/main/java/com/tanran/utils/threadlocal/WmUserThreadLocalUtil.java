package com.tanran.utils.threadlocal;

import com.tanran.model.media.pojos.WmUser;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/5/12 9:52
 */
public class WmUserThreadLocalUtil {
    private final  static ThreadLocal<WmUser> userThreadLocal = new ThreadLocal<>();


    /**
     * 设置当前线程中的用户
     * @param user
     */
    public static void setUser(WmUser user){
        userThreadLocal.remove();
        userThreadLocal.set(user);
    }

    /**
     * 获取线程中的用户
     * @return
     */
    public static WmUser getUser( ){
        return userThreadLocal.get();
    }
}
