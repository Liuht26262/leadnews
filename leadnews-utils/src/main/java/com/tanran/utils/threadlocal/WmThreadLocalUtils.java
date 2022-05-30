package com.tanran.utils.threadlocal;

import com.tanran.model.media.pojos.WmUser;


public class WmThreadLocalUtils extends ThreadLocal{

    private final  static ThreadLocal<WmUser> userThreadLocal = new ThreadLocal<>();


    /**
     * 设置当前线程中的用户
     * @param user
     */
    public static void setUser(WmUser user){
        System.out.println("set线程"+Thread.currentThread().getName());
        userThreadLocal.set(user);
    }

    /**
     * 获取线程中的用户
     * @return
     */
    public static WmUser getUser( ){
        return userThreadLocal.get();
    }

    @Override
    protected WmUser initialValue() {
        return new WmUser();
    }


}
