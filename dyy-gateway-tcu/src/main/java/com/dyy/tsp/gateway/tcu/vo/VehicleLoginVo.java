package com.dyy.tsp.gateway.tcu.vo;

import com.dyy.tsp.core.evgb.entity.VehicleLogin;

@SuppressWarnings("all")
public class VehicleLoginVo extends VehicleLogin {

    private String vin;

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
}
