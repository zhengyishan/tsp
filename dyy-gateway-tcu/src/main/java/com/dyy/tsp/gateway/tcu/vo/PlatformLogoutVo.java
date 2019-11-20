package com.dyy.tsp.gateway.tcu.vo;

import com.dyy.tsp.core.evgb.entity.PlatformLogout;

/**
 * 平台登出
 */
@SuppressWarnings("all")
public class PlatformLogoutVo  extends PlatformLogout {

    private String vin;

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
}
