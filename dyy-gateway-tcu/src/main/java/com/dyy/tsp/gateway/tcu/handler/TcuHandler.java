package com.dyy.tsp.gateway.tcu.handler;

import com.alibaba.fastjson.JSONObject;
import com.dyy.tsp.common.enumtype.LibraryType;
import com.dyy.tsp.core.evgb.entity.DataBody;
import com.dyy.tsp.core.evgb.entity.EvGBProtocol;
import com.dyy.tsp.core.evgb.enumtype.CommandType;
import com.dyy.tsp.core.evgb.enumtype.ResponseType;
import com.dyy.tsp.gateway.tcu.config.TcuProperties;
import com.dyy.tsp.gateway.tcu.netty.TcuChannel;
import com.dyy.tsp.gateway.tcu.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TCU模拟处理器
 */
@Service
@SuppressWarnings("all")
public class TcuHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TcuHandler.class);

    @Autowired
    private RedisHandler redisHandler;

    @Autowired
    private TcuProperties tcuProperties;

    public void commandDown(CommandDownRequestVo commandDownRequestVo) {
        redisHandler.getRedisTemplateByType(LibraryType.COMMAND).convertAndSend(tcuProperties.getCommandRequestTopic(), JSONObject.toJSONString(commandDownRequestVo));
    }

    public void vehicleLogin(VehicleLoginVo vehicleLoginVo) {
        EvGBProtocol protocol = new EvGBProtocol();
        protocol.setCommandType(CommandType.VEHICLE_LOGIN);
        protocol.setResponseType(ResponseType.COMMAND);
        protocol.setVin(vehicleLoginVo.getVin());
        protocol.setBody(new DataBody(vehicleLoginVo.encode()));
        TcuChannel.INSTANCE.getChannelHandlerContext().channel().writeAndFlush(protocol.encode());
        LOGGER.debug("{} {}",vehicleLoginVo.getVin(),protocol.getCommandType().getDesc());
    }

    public void vehicleLogout(VehicleLogoutVo vehicleLogoutVo) {
        EvGBProtocol protocol = new EvGBProtocol();
        protocol.setCommandType(CommandType.VEHICLE_LOGOUT);
        protocol.setResponseType(ResponseType.COMMAND);
        protocol.setVin(vehicleLogoutVo.getVin());
        protocol.setBody(new DataBody(vehicleLogoutVo.encode()));
        TcuChannel.INSTANCE.getChannelHandlerContext().channel().writeAndFlush(protocol.encode());
        LOGGER.debug("{} {}",vehicleLogoutVo.getVin(),protocol.getCommandType().getDesc());
    }

    public void platformLogin(PlatformLoginVo platformLoginVo) {
        EvGBProtocol protocol = new EvGBProtocol();
        protocol.setCommandType(CommandType.PLATFORM_LOGIN);
        protocol.setResponseType(ResponseType.COMMAND);
        protocol.setVin(platformLoginVo.getVin());
        protocol.setBody(new DataBody(platformLoginVo.encode()));
        TcuChannel.INSTANCE.getChannelHandlerContext().channel().writeAndFlush(protocol.encode());
        LOGGER.debug("{} {}",platformLoginVo.getVin(),protocol.getCommandType().getDesc());
    }

    public void platformLogout(PlatformLogoutVo platformLogoutVo) {
        EvGBProtocol protocol = new EvGBProtocol();
        protocol.setCommandType(CommandType.PLATFORM_LOGOUT);
        protocol.setResponseType(ResponseType.COMMAND);
        protocol.setVin(platformLogoutVo.getVin());
        protocol.setBody(new DataBody(platformLogoutVo.encode()));
        TcuChannel.INSTANCE.getChannelHandlerContext().channel().writeAndFlush(protocol.encode());
        LOGGER.debug("{} {}",platformLogoutVo.getVin(),protocol.getCommandType().getDesc());
    }

    public void realtimeData(RealTimeDataVo realTimeDataVo) {
        EvGBProtocol protocol = new EvGBProtocol();
        protocol.setCommandType(CommandType.valuesOf(realTimeDataVo.getCommandType()));
        protocol.setResponseType(ResponseType.COMMAND);
        protocol.setVin(realTimeDataVo.getVin());
        protocol.setBody(new DataBody(realTimeDataVo.encode()));
        TcuChannel.INSTANCE.getChannelHandlerContext().channel().writeAndFlush(protocol.encode());
        LOGGER.debug("{} {}",realTimeDataVo.getVin(),protocol.getCommandType().getDesc());
    }

    public void checkTime(String vin) {
        EvGBProtocol protocol = new EvGBProtocol();
        protocol.setCommandType(CommandType.TERMINAL_CHECK_TIME);
        protocol.setResponseType(ResponseType.COMMAND);
        protocol.setVin(vin);
        protocol.setBody(null);
        TcuChannel.INSTANCE.getChannelHandlerContext().channel().writeAndFlush(protocol.encode());
        LOGGER.debug("{} {}",vin,protocol.getCommandType().getDesc());
    }
}
