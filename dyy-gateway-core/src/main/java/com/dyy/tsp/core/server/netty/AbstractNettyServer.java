package com.dyy.tsp.core.server.netty;

import com.dyy.tsp.common.exception.BaseException;
import com.dyy.tsp.core.server.IServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * created by dyy
 */
@SuppressWarnings("all")
public abstract class AbstractNettyServer implements IServer {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractNettyServer.class);

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    protected int port;

    protected int timeout;

    protected int soBackLog;

    public void start() {
        LOG.debug("Start server ...");
        bossGroup = new NioEventLoopGroup(2);
        workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                 .channel(NioServerSocketChannel.class)
                 .option(ChannelOption.SO_BACKLOG, soBackLog)//三次握手队列长度
                 .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)//建立连接超时时间
                 .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                 .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                 .childOption(ChannelOption.SO_KEEPALIVE,Boolean.FALSE)
                 .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                 .childHandler(getChannelInitializer());
        try {
            ChannelFuture f = bootstrap.bind(port).sync();
            LOG.debug("Start server succeed!");
        } catch (InterruptedException e) {
            throw new BaseException("Server start error!", e);
        }
    }

    public abstract ChannelInitializer<SocketChannel> getChannelInitializer();

    public void stop() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getTimeout() {
        return timeout;
    }

    public int getSoBackLog() {
        return soBackLog;
    }

    public void setSoBackLog(int soBackLog) {
        this.soBackLog = soBackLog;
    }
}
