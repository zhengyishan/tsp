package com.dyy.tsp.gateway.server.handler;

import com.dyy.tsp.core.base.AbstractBusinessHandler;
import com.dyy.tsp.core.evgb.entity.BeanTime;
import com.dyy.tsp.core.evgb.entity.DataBody;
import com.dyy.tsp.core.evgb.entity.EvGBProtocol;
import com.dyy.tsp.core.evgb.enumtype.ResponseType;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 终端校时处理器。
 * 需要给终端响应
 * created by dyy
 */
@Service
@SuppressWarnings("all")
public class CheckTimeHandler extends AbstractBusinessHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckTimeHandler.class);

    /**
     * 响应当前服务器时间戳
     * @param protocol
     * @param channel
     */
    @Override
    public void doBusiness(EvGBProtocol protocol, Channel channel) {
        protocol.setResponseType(ResponseType.SUCCESS);
        protocol.setBody(new DataBody(new BeanTime(System.currentTimeMillis()).encode()));
        channel.writeAndFlush(protocol.encode());
        LOGGER.debug("{} {} 响应{}",protocol.getVin(),protocol.getCommandType().getDesc());
    }

}
