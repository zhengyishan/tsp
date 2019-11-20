package com.dyy.tsp.core.base;

import com.dyy.tsp.common.entity.VehicleCache;
import com.dyy.tsp.core.evgb.entity.EvGBProtocol;
import com.dyy.tsp.core.evgb.enumtype.ResponseType;
import io.netty.channel.Channel;

/**
 * 业务处理类
 * created by dyy
 */
@SuppressWarnings("all")
public interface IHandler {

    Boolean checkLogin(VehicleCache vehicleCache);

    void doBusiness(EvGBProtocol evGBProtocol, Channel channel);

    void doCommonResponse(ResponseType responseType, EvGBProtocol protrocol, Channel channel);
}
