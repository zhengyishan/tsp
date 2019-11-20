package com.dyy.tsp.core.base;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

/**
 * 抽象编码
 */
@SuppressWarnings("all")
public abstract class AbstractDecode<T extends IProtocol> extends ByteToMessageDecoder {

    protected IRedis redisHandler;

    protected IParse parseHandler;

    /**
     * 当Flag为True时解析上行
     * 当Flag为false时解析下行
     */
    protected Boolean flag;

    /**
     * 当为true时是内存合并
     * 当为false时是Redis合并
     */
    protected Boolean memoryConsolidation;

    public AbstractDecode() {
    }

    public AbstractDecode(IRedis redisHandler, IParse parseHandler, Boolean flag, Boolean memoryConsolidation) {
        this.redisHandler = redisHandler;
        this.parseHandler = parseHandler;
        this.flag = flag;
        this.memoryConsolidation = memoryConsolidation;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> out) throws Exception {
        Object decoded = this.decode(channelHandlerContext, byteBuf);
        if (decoded != null) {
            out.add(decoded);
        }
    }

    abstract protected T decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception;

    public IRedis getRedisHandler() {
        return redisHandler;
    }

    public void setRedisHandler(IRedis redisHandler) {
        this.redisHandler = redisHandler;
    }

    public IParse getParseHandler() {
        return parseHandler;
    }

    public void setParseHandler(IParse parseHandler) {
        this.parseHandler = parseHandler;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

}
