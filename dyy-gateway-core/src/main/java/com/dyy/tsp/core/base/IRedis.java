package com.dyy.tsp.core.base;

import com.dyy.tsp.common.enumtype.LibraryType;
import java.util.concurrent.TimeUnit;

/**
 * Redis顶层操作接口
 * created by dyy
 */
@SuppressWarnings("all")
public interface IRedis {

    /**
     * 判断key是否存在
     * @param type
     * @param key
     * @return
     */
    public boolean hasKey(LibraryType type, String key);

    /**
     * 设置key
     * @param type
     * @param key
     * @param value
     */
    public void set(LibraryType type, String key, String value);

    /**
     * 设置key并设置有效时间
     * @param type
     * @param key
     * @param value
     * @param timeout
     * @param timeUnit
     */
    public void set(LibraryType type, String key, String value, Long timeout, TimeUnit timeUnit);

    /**
     * 设置key的有效时间  多次设置为叠加
     * @param type
     * @param key
     * @param timeout
     * @param timeUnit
     */
    public void expire(LibraryType type, String key, Long timeout, TimeUnit timeUnit);

    /**
     * 删除Key
     * @param type
     * @param key
     */
    public void delete(LibraryType type, String key);

    /**
     * 获取一个key的value
     * @param type
     * @param key
     * @return
     */
    public String get(LibraryType type, String key);
}
