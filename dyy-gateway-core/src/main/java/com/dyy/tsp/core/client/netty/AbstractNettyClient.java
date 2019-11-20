package com.dyy.tsp.core.client.netty;

import com.dyy.tsp.common.exception.BaseException;
import com.dyy.tsp.core.client.ClientEventListener;
import com.dyy.tsp.core.client.IClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * created by dyy
 */
@SuppressWarnings("all")
public abstract class AbstractNettyClient implements IClient {
    private static Logger LOG = LoggerFactory.getLogger(AbstractNettyClient.class);

    protected String ip;

    protected int port;

    protected Channel channel;

    protected int timeout;

    protected Bootstrap bootstrap;

    protected volatile boolean isConnected;

    protected volatile boolean isClosed;

    protected int connectNum;

    protected int connectMaxNum;

    private ExecutorService executor = Executors.newFixedThreadPool(8);

    public AbstractNettyClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public AbstractNettyClient() {
    }

    private NioEventLoopGroup workerGroup;


    public void connect(String ip, int port, ClientEventListener listener) {
        try {
            workerGroup = new NioEventLoopGroup();
            bootstrap = new Bootstrap();
            bootstrap.group(workerGroup).channel(NioSocketChannel.class)
                    .remoteAddress(ip, port)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(getChannelInitializer());
            channel = bootstrap.connect().addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if(future.isSuccess()) {
                        LOG.debug("Client connect succeed!");
                        isConnected = true;
                        connectNum = 0;
                        if(listener != null) {
                            listener.onConnected();
                        }
                    }
                }
            }).sync().channel();
            channel.closeFuture().addListener(new ChannelFutureListener(){
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    isConnected = false;
                    LOG.debug("Client connection closed!");
                    if(listener != null) {
                        listener.onClosed();
                    }
                }
            }).sync();
        } catch (Exception e) {
            throw new BaseException("Client connect error!", e);
        } finally {
            isConnected = false;
            workerGroup.shutdownGracefully();
            reConnect();
        }
    }

    public void connect() {
        connect(null);
    }

    public void connect(ClientEventListener listener) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                connect(ip, port, listener);
            }
        });
    }

    public boolean isClose() {
        return isClosed;
    }

    public boolean isConnect() {
        return isConnected;
    }

    public void setClosed(boolean closed) {
        this.isClosed = closed;
    }

    public void close() {
        if(!isClosed && channel != null && channel.isOpen()) {
            //channel.close();
            isClosed = true;
        }
    }

    public void reConnect() {
        if (!isClosed && !isConnected && (connectNum++ < connectMaxNum)) {
            LOG.debug("Client re connect num: {}", connectNum);
            try {
                Thread.sleep(2000);
                connect();
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    public abstract ChannelInitializer<SocketChannel> getChannelInitializer();

    public void start() {
        connect();
    }

    public void stop() {
        workerGroup.shutdownGracefully();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getConnectMaxNum() {
        return connectMaxNum;
    }

    public void setConnectMaxNum(int connectMaxNum) {
        this.connectMaxNum = connectMaxNum;
    }
}
