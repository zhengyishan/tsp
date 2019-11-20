package com.dyy.tsp.core.handler;

import com.dyy.tsp.core.evgb.entity.EvGBProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Netty抽象逻辑处理层
 * created by dyy
 */
@SuppressWarnings("all")
public abstract class AbstractNettyHandler extends SimpleChannelInboundHandler<EvGBProtocol> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, EvGBProtocol protocol) throws Exception {
        doLogic(channelHandlerContext,protocol);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx)  throws Exception{
        super.channelActive(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx)  throws Exception{
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx,cause);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception{
        super.userEventTriggered(ctx,evt);
    }

    public abstract void doLogic(ChannelHandlerContext ctx, EvGBProtocol protocol);
}
