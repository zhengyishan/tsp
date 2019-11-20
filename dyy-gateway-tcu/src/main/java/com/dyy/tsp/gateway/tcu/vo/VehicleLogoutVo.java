package com.dyy.tsp.gateway.tcu.vo;

import com.dyy.tsp.core.evgb.entity.VehicleLogout;

@SuppressWarnings("all")
public class VehicleLogoutVo extends VehicleLogout {

    private String vin;

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
}
