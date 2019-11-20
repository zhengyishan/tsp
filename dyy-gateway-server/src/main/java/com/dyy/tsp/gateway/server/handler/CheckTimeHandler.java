package com.dyy.tsp.gateway.server.handler;

import com.dyy.tsp.core.base.AbstractBusinessHandler;
import com.dyy.tsp.core.evgb.entity.EvGBProtocol;
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

    @Override
    public void doBusiness(EvGBProtocol protrocol, Channel channel) {

    }
}
