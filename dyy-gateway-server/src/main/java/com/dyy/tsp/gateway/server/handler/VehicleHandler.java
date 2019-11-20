package com.dyy.tsp.gateway.server.handler;

import com.dyy.tsp.core.base.AbstractBusinessHandler;
import com.dyy.tsp.core.evgb.entity.EvGBProtocol;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 车辆登入与车辆登出处理器
 * 需要给出终端对应响应
 * created by dyy
 */
@Service
@SuppressWarnings("all")
public class VehicleHandler extends AbstractBusinessHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleHandler.class);

    @Autowired
    private ForwardHandler forwardHandler;

    @Override
    public void doBusiness(EvGBProtocol protrocol, Channel channel) {
        switch (protrocol.getCommandType()){
            case VEHICLE_LOGIN: {
                this.doVehicleLogin(protrocol,channel);
                break;
            }
            case VEHICLE_LOGOUT:{
                this.doVehicleLogout(protrocol,channel);
                break;
            }
            default:
                break;
        }
    }

    /**
     * 车辆登入
     * @param protrocol
     * @param channel
     */
    private void doVehicleLogout(EvGBProtocol protrocol, Channel channel) {
    }

    /**
     * 车辆登出
     * @param protrocol
     * @param channel
     */
    private void doVehicleLogin(EvGBProtocol protrocol, Channel channel) {

    }
}
