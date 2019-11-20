package com.dyy.tsp.core.base;

import com.dyy.tsp.common.enumtype.LibraryType;
import java.util.concurrent.TimeUnit;

/**
 * 实现顶层方法，子类重写对应方法
 * created by dyy
 */
@SuppressWarnings("all")
public class AbstractRedisHandler implements IRedis {

    @Override
    public boolean hasKey(LibraryType type, String key) {
        return false;
    }

    @Override
    public void set(LibraryType type, String key, String value) {

    }

    @Override
    public void set(LibraryType type, String key, String value, Long timeout, TimeUnit timeUnit) {

    }

    @Override
    public void expire(LibraryType type, String key, Long timeout, TimeUnit timeUnit) {

    }

    @Override
    public void delete(LibraryType type, String key) {

    }

    @Override
    public String get(LibraryType type, String key) {
        return null;
    }
}
