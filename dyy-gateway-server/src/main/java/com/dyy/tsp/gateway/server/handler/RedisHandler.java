package com.dyy.tsp.gateway.server.handler;

import com.dyy.tsp.common.enumtype.LibraryType;
import com.dyy.tsp.core.base.AbstractRedisHandler;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Redis操作类
 * created by dyy
 */
@Service
@SuppressWarnings("all")
public class RedisHandler extends AbstractRedisHandler {

    @Resource(name="vehicleRedisTemplate")
    private RedisTemplate<String,String> vehicleRedisTemplate;

    /**
     * 判断key是否存在
     * @param type
     * @param key
     * @return
     */
    @Override
    public boolean hasKey(LibraryType type, String key){
        return this.getRedisTemplateByType(type).hasKey(key);
    }

    /**
     * 设置key
     * @param type
     * @param key
     * @param value
     */
    @Override
    public void set(LibraryType type,String key,String value){
        this.getRedisTemplateByType(type).opsForValue().set(key,value);
    }

    /**
     * 设置key并设置有效时间
     * @param type
     * @param key
     * @param value
     * @param timeout
     * @param timeUnit
     */
    @Override
    public void set(LibraryType type,String key,String value,Long timeout,TimeUnit timeUnit){
        this.getRedisTemplateByType(type).opsForValue().set(key,value,timeout, timeUnit);
    }

    /**
     * 设置key的有效时间  多次设置为叠加
     * @param type
     * @param key
     * @param timeout
     * @param timeUnit
     */
    @Override
    public void expire(LibraryType type,String key,Long timeout,TimeUnit timeUnit){
        this.getRedisTemplateByType(type).expire(key,timeout, timeUnit);
    }

    /**
     * 获取redis中key的过期时间
     * @param type
     * @param key
     * @param timeUnit
     */
    public long getExpire(String key,TimeUnit timeUnit,LibraryType type){
        return this.getRedisTemplateByType(type).getExpire(key,timeUnit);
    }

    /**
     * 删除Key
     * @param type
     * @param key
     */
    @Override
    public void delete(LibraryType type,String key){
        this.getRedisTemplateByType(type).delete(key);
    }

    /**
     * 获取一个key的value
     * @param type
     * @param key
     * @return
     */
    @Override
    public String get(LibraryType type, String key){
        return this.getRedisTemplateByType(type).opsForValue().get(key);
    }

    /**
     * 根据类型获取对应的RedisTemplate
     * @param type
     * @return
     */
    private RedisTemplate<String,String> getRedisTemplateByType(LibraryType type){
        switch (type){
            case VEHICLE:{
                return vehicleRedisTemplate;
            }
            default:
                return null;
        }
    }

}
