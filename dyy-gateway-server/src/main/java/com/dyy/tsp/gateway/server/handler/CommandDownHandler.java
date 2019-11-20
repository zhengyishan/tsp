package com.dyy.tsp.gateway.server.handler;

import com.alibaba.fastjson.JSONObject;
import com.dyy.tsp.common.entity.CommandDownRequest;
import com.dyy.tsp.gateway.server.common.CachePrefixEnum;
import com.dyy.tsp.gateway.server.common.CommonCache;
import com.dyy.tsp.gateway.server.enumtype.CommandDownHelperType;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 指令下发处理器
 * created by dyy
 */
@Service
@SuppressWarnings("all")
public class CommandDownHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandDownHandler.class);

    /**
     * 处理网关接收到所有指令
     * 关闭监控
     * 开启监控
     * 清除内存
     * 远控指令下发
     * @param message
     */
    public void doCommandDown(String message){
        LOGGER.debug("receive command request {}",message);
        CommandDownRequest request = JSONObject.parseObject(message, CommandDownRequest.class);
        if(CommandDownHelperType.CLOSE_DEBUG.name().equals(request.getCommand())){
            CommonCache.debugVinMap.remove(request.getVin());
            LOGGER.debug("{} webSocket console close",request.getVin());
        }else if(CommandDownHelperType.OPEN_DEBUG.name().equals(request.getCommand())){
            CommonCache.debugVinMap.put(request.getVin(),request.getCommand());
            LOGGER.debug("{} webSocket console open",request.getVin());
        }else if(CommandDownHelperType.CLEAR_CAHCE.name().equals(request.getCommand())){
            CommonCache.vehicleCacheMap.remove(CachePrefixEnum.VEHICLE_CACHE.getPrefix()+request.getVin());
        }else {
            Channel channel = CommonCache.vinChannelMap.get(request.getVin());
            if(channel!=null && channel.isActive()){
                this.sendCommand(channel,request);
            }
        }
    }

    /**
     * 网关下发对应指令给终端
     * @param channel
     * @param request
     */
    private void sendCommand(Channel channel, CommandDownRequest request) {

    }

}
