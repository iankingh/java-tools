package com.ian.tools.redis;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 
 * 參考 springboot 整合redis 使用(附RedisUtil类)_安然无恙的博客-CSDN博客
 * https://blog.csdn.net/qq_36635434/article/details/103992863
 * 
 * 本類別詳細說明。
 * <p/>
 * Package: com.ian.utils <br>
 * File Name: RedisStrUtils <br>
 * <p/>
 * Purpose: <br>
 * 
 * @ClassName: com.ian.RedisStrUtils
 * @Description: RedisStrUtils 使用 StringRedisTemplate 的 工具類
 * @Company: Team.
 * @author Ian
 * @version 1.0, April 01, 2021
 */
public class RedisStrUtils {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /** -------------------key相關操作--------------------- */

    /**
     * 刪除key
     * 
     * @param key
     */
    public void delete(String key) {
        if (hasKey(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 批量刪除key
     * 
     * @param keys
     */
    public void delete(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 是否存在key
     * 
     * @param key
     * @return true 存在 ; false不存在
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 
     *
     * 設置過期的時間
     * 
     * @param key
     * @param time 時間(秒)
     * @return
     */
    public boolean expire(String key, long time) {
        if (time > 0) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
            return true;
        } else {
            return false;
        }
    }

    /*
     * 
     * 根據 key 獲得過期時間
     *
     * @param key 不能為null
     * 
     * @return 時間(秒) = 0 代表永久有效
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }


    /** -------------------string 相關操作--------------------- */

}
