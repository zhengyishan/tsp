package com.dyy.tsp.core.base;

import com.dyy.tsp.common.entity.VehicleCache;
import com.dyy.tsp.core.evgb.enumtype.ResponseType;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dyy.tsp.core.evgb.entity.EvGBProtocol;

/**
 * 实现顶层方法，子类重写对应方法
 * 公用抽取
 * created by dyy
 */
@SuppressWarnings("all")
public abstract class AbstractBusinessHandler implements IHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBusinessHandler.class);

    @Override
    public Boolean checkLogin(VehicleCache vehicleCache) {
        return vehicleCache != null ? vehicleCache.getLogin() : Boolean.FALSE;
    }

    /**
     * 公用业务处理方法
     * @param protrocol
     * @param channel
     */
    @Override
    public abstract void doBusiness(EvGBProtocol protrocol, Channel channel);

    @Override
    public void doCommonResponse(ResponseType responseType,EvGBProtocol protrocol,Channel channel) {
        protrocol.setBody(null);
        protrocol.setResponseType(responseType);
        channel.writeAndFlush(Unpooled.wrappedBuffer(protrocol.encode()));
        LOGGER.debug("{} {} 响应{}",protrocol.getVin(),protrocol.getCommandType().getDesc());
    }

}
