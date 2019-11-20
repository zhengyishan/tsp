package com.dyy.tsp.gateway.server.common;

/**
 * Redis缓存前缀枚举类
 * created by dyy
 */
@SuppressWarnings("all")
public enum CachePrefixEnum {

    VEHICLE_CACHE("dyy:gateway:vehicle:","车辆信息缓存"),

    ;
    private String prefix;
    private String desc;

    CachePrefixEnum(String prefix, String desc) {
        this.prefix = prefix;
        this.desc = desc;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
