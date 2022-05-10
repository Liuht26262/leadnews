package com.tanran.utils.threadlocal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.tanran.model.user.pojos.ApUser;

public class AppThreadLocalUtils {

    private final  static ThreadLocal userThreadLocal = new ThreadLocal<>();
    private static ApUser user;

    @Autowired
    private static RedisTemplate redisTemplate;

    /**
     * 将此时的用户信息存到缓存中
     * @param user
     */
    public static void setUser(ApUser user){
        userThreadLocal.set(user);
        AppThreadLocalUtils.user = user;
        /**用id和加密盐进行加密*/
        // String key = DigestUtils.md5DigestAsHex((user.getId() + user.getSalt()).getBytes());
        // HashMap<String, Object> userMap = new HashMap<>();
        // userMap.put(key, user);
        // redisTemplate.opsForHash().putAll("user/"+user.getId(),userMap);
    }

    /**
     * 获取线程中的用户
     * @return
     */
    public static ApUser getUser( ){
        return user;
    }

}
