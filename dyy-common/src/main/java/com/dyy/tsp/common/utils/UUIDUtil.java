package com.dyy.tsp.common.utils;

import java.util.UUID;

/**
 * UUIDUtil
 * created by dyy
 */
@SuppressWarnings("all")
public class UUIDUtil {

    /**
     * 通过jdk自带的uuid生成器生成36位的uuid
     * @return
     */
    public static String get36UUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 通过jdk自带的uuid生成器生成32位的uuid
     * @return
     */
    public static String get32UUID() {
        return UUID.randomUUID().toString().replace("-","");
    }

}
