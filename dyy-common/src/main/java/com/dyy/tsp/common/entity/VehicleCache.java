package com.dyy.tsp.common.entity;

@SuppressWarnings("all")
public class VehicleCache {

    //是否车辆登入
    private Boolean login;

    //最后一次登入时间
    private String lastLoginTime;

    //最后一次登出时间
    private String lastLogoutTime;

    public Boolean getLogin() {
        return login;
    }

    public void setLogin(Boolean login) {
        this.login = login;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLogoutTime() {
        return lastLogoutTime;
    }

    public void setLastLogoutTime(String lastLogoutTime) {
        this.lastLogoutTime = lastLogoutTime;
    }
}
