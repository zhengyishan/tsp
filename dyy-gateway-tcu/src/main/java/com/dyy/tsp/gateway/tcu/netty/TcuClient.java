package com.dyy.tsp.gateway.tcu.netty;

import com.dyy.tsp.core.base.EvGBDecode;
import com.dyy.tsp.core.client.netty.AbstractNettyClient;
import com.dyy.tsp.gateway.tcu.config.TcuProperties;
import com.dyy.tsp.gateway.tcu.handler.RedisHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;

/**
 * TCU模拟终端
 * created by dyy
 */
@SuppressWarnings("all")
public class TcuClient extends AbstractNettyClient {

    public TcuClient(String ip, int port) {
        super(ip, port);
    }

    @Autowired
    private TcuProperties tcuProperties;

    @Autowired
    private RedisHandler redisHandler;

    @Autowired
    private TcuParseHandler tcuParseHandler;

    @Autowired
    private TcuNettyHandler tcuNettyHandler;

    public ChannelInitializer<SocketChannel> getChannelInitializer() {
        return new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(
                        new LoggingHandler(LogLevel.DEBUG),
                        new LengthFieldBasedFrameDecoder(tcuProperties.getMaxFrameLength(), tcuProperties.getLengthFieldOffset()
                                , tcuProperties.getLengthFieldLength(), tcuProperties.getLengthAdjustment()
                                , tcuProperties.getInitialBytesToStrip(), tcuProperties.getFailFast()),
                        new EvGBDecode(redisHandler,tcuParseHandler,Boolean.FALSE,Boolean.TRUE),
                        new IdleStateHandler(tcuProperties.getTimeout(),tcuProperties.getTimeout(), 0),
                        tcuNettyHandler
                );
            }
        };
    }

    @PostConstruct
    @Override
    public void connect() {
        super.connect();
    }

}
