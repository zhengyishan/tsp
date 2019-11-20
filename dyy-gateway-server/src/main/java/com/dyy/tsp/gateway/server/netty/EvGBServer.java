package com.dyy.tsp.gateway.server.netty;

import com.dyy.tsp.core.base.EvGBDecode;
import com.dyy.tsp.core.server.netty.AbstractNettyServer;
import com.dyy.tsp.gateway.server.config.EvGBProperties;
import com.dyy.tsp.gateway.server.handler.RedisHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 基于GB32960数据接入网关服务
 * 开放端口8111
 * created by dyy
 */
@SuppressWarnings("all")
public class EvGBServer extends AbstractNettyServer {

    @Autowired
    private EvGBHandler evGBHandler;

    @Autowired
    private RedisHandler redisHandler;

    @Autowired
    private EvGBParseHandler evGBParseHandler;

    @Autowired
    private EvGBProperties evGBProperties;

    @Override
    public ChannelInitializer<SocketChannel> getChannelInitializer() {
        return new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(
                        new LengthFieldBasedFrameDecoder(evGBProperties.getMaxFrameLength(), evGBProperties.getLengthFieldOffset()
                                , evGBProperties.getLengthFieldLength(), evGBProperties.getLengthAdjustment()
                                , evGBProperties.getInitialBytesToStrip(), evGBProperties.getFailFast()),
                        new EvGBDecode(redisHandler,evGBParseHandler,Boolean.TRUE,Boolean.TRUE),
                        new IdleStateHandler(evGBProperties.getTimeout(),evGBProperties.getTimeout(), 0),
                        evGBHandler
                );
            }
        };
    }

    @PostConstruct
    @Override
    public void start() {
        super.start();
    }

    @PreDestroy
    @Override
    public void stop() {
        super.stop();
    }
}
