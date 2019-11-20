package com.dyy.tsp.gateway.server.enumtype;

public enum CommandDownHelperType {

    CLOSE_DEBUG("关闭监控"),
    OPEN_DEBUG("开启监控"),
    CLEAR_CAHCE("清除内存"),
    ;
    private String desc;

    CommandDownHelperType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
