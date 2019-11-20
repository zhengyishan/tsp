package com.dyy.tsp.gateway.server.handler;

import com.dyy.tsp.common.entity.VehicleCache;
import com.dyy.tsp.core.base.AbstractBusinessHandler;
import com.dyy.tsp.core.evgb.entity.EvGBProtocol;
import com.dyy.tsp.core.evgb.enumtype.ResponseType;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 心跳处理器。需要给终端响应
 * 终端的心跳频率应保持4秒1次。实时信息上报为10秒1次。2次时间间隔必须小于10秒,终端才能处理
 * 终端在两次心跳失败或者无法获取响应时，应按照国家标准将实时信息上报变更为补发信息上报
 * created by dyy
 */
@Service
@SuppressWarnings("all")
public class HeartBeatHandler extends AbstractBusinessHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeartBeatHandler.class);

    @Override
    public void doBusiness(EvGBProtocol protrocol, Channel channel) {
        VehicleCache vehicleCache = protrocol.getVehicleCache();
        if(!this.checkLogin(vehicleCache)){
            protrocol.setResponseType(ResponseType.FAILURE);
        }else{
            protrocol.setResponseType(ResponseType.SUCCESS);
        }
        channel.writeAndFlush(protrocol.encode());
    }
}
