package com.dyy.tsp.gateway.tcu.netty;

import com.alibaba.fastjson.JSONObject;
import com.dyy.tsp.core.evgb.entity.EvGBProtocol;
import com.dyy.tsp.core.evgb.enumtype.CommandType;
import com.dyy.tsp.core.evgb.enumtype.ResponseType;
import com.dyy.tsp.core.handler.AbstractNettyHandler;
import com.dyy.tsp.gateway.tcu.config.TcuProperties;
import com.dyy.tsp.gateway.tcu.handler.BusinessHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * created by dyy
 */
@ChannelHandler.Sharable
@Service
@SuppressWarnings("all")
public class TcuNettyHandler extends AbstractNettyHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(TcuNettyHandler.class);

    @Autowired
    private TcuProperties tcuProperties;

    @Autowired
    private BusinessHandler businessHandler;

    @Override
    public void doLogic(ChannelHandlerContext ctx, EvGBProtocol protocol) {
        LOGGER.debug("parse protocol:{}", JSONObject.toJSONString(protocol));
        businessHandler.doBusiness(protocol,ctx.channel());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        TcuChannel.INSTANCE.setChannelHandlerContext(ctx);
        LOGGER.debug("server ip[{}] connected succeed",ctx.channel().remoteAddress().toString());
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.channel().close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt){
        if(evt instanceof IdleStateEvent){
            IdleState state = ((IdleStateEvent)evt).state();
            if(state == IdleState.WRITER_IDLE){
                ByteBuf heartBuf = this.getHeartBeat();
                ctx.channel().writeAndFlush(heartBuf);
            }
        }
    }

    /**
     * 获取模拟心跳包
     * @return
     */
    private ByteBuf getHeartBeat() {
        EvGBProtocol evGBProtocol = new EvGBProtocol();
        evGBProtocol.setVin(tcuProperties.getHeartVin());
        evGBProtocol.setCommandType(CommandType.HEARTBEAT);
        evGBProtocol.setBody(null);
        evGBProtocol.setResponseType(ResponseType.COMMAND);
        return evGBProtocol.encode();
    }
}
