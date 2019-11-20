package com.dyy.tsp.gateway.server.handler;

import com.alibaba.fastjson.JSONObject;
import com.dyy.tsp.common.entity.VehicleCache;
import com.dyy.tsp.common.enumtype.LibraryType;
import com.dyy.tsp.core.base.AbstractBusinessHandler;
import com.dyy.tsp.core.evgb.entity.EvGBProtocol;
import com.dyy.tsp.core.evgb.entity.VehicleLogin;
import com.dyy.tsp.core.evgb.entity.VehicleLogout;
import com.dyy.tsp.core.evgb.enumtype.ResponseType;
import com.dyy.tsp.gateway.server.common.CommonCache;
import com.dyy.tsp.gateway.server.util.HelperKeyUtil;
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

    @Autowired
    private RedisHandler redisHandler;

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
    private void doVehicleLogin(EvGBProtocol protrocol, Channel channel) {
        VehicleLogin vehicleLogin = protrocol.getBody().getJson().toJavaObject(VehicleLogin.class);
        VehicleCache vehicleCache = protrocol.getVehicleCache();
        String redisKey = HelperKeyUtil.getKey(protrocol.getVin());
        vehicleCache.setLastLoginTime(vehicleLogin.getBeanTime().formatTime());
        vehicleCache.setLastLoginSerialNum(vehicleLogin.getSerialNum());
        vehicleCache.setLogin(Boolean.TRUE);
        redisHandler.set(LibraryType.VEHICLE,redisKey,JSONObject.toJSONString(vehicleCache));
        CommonCache.vehicleCacheMap.put(redisKey,vehicleCache);
        CommonCache.vinChannelMap.put(protrocol.getVin(),channel);
        CommonCache.channelVinMap.put(channel,protrocol.getVin());
        forwardHandler.sendToDispatcher(protrocol);
        doCommonResponse(ResponseType.SUCCESS,protrocol,channel);
    }

    /**
     * 车辆登出
     * @param protrocol
     * @param channel
     */
    private void doVehicleLogout(EvGBProtocol protrocol, Channel channel) {
        VehicleLogout vehicleLogout = protrocol.getBody().getJson().toJavaObject(VehicleLogout.class);
        VehicleCache vehicleCache = protrocol.getVehicleCache();
        String redisKey = HelperKeyUtil.getKey(protrocol.getVin());
        vehicleCache.setLastLogoutTime(vehicleLogout.getBeanTime().formatTime());
        vehicleCache.setLastLogoutSerialNum(vehicleLogout.getSerialNum());
        vehicleCache.setLogin(Boolean.FALSE);
        redisHandler.set(LibraryType.VEHICLE,redisKey,JSONObject.toJSONString(vehicleCache));
        CommonCache.vehicleCacheMap.remove(redisKey);
        CommonCache.vinChannelMap.remove(protrocol.getVin());
        CommonCache.channelVinMap.remove(channel);
        forwardHandler.sendToDispatcher(protrocol);
        doCommonResponse(ResponseType.SUCCESS,protrocol,channel);
    }
}
