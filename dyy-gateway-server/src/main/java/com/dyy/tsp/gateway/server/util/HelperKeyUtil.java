package com.dyy.tsp.gateway.server.util;

import com.dyy.tsp.gateway.server.common.CachePrefixEnum;

public class HelperKeyUtil {

    /**
     * 生成内存中车辆缓存固定规则Key
     * @param vin
     * @return
     */
    public static String getKey(String vin) {
        StringBuffer sb = new StringBuffer();
        sb.append(CachePrefixEnum.VEHICLE_CACHE.getPrefix());
        sb.append(vin);
        return sb.toString();
    }
}
