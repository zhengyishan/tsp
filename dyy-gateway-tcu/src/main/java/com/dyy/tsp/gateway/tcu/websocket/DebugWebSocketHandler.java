package com.dyy.tsp.gateway.tcu.websocket;

import com.alibaba.fastjson.JSONObject;
import com.dyy.tsp.common.entity.CommandDownRequest;
import com.dyy.tsp.common.enumtype.LibraryType;
import com.dyy.tsp.gateway.tcu.handler.RedisHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 网关WebSocket监控处理类
 * created by dyy
 */
@SuppressWarnings("all")
@Service
public class DebugWebSocketHandler implements WebSocketHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DebugWebSocketHandler.class);

    //在线监控设备列表
    private static final Map<String, WebSocketSession> devices = new ConcurrentHashMap<>();

    private static final String CLOSE_DEBUG = "CLOSE_DEBUG";

    private static final String OPEN_DEBUG = "OPEN_DEBUG";

    private static final String SRM_COMMAND_REQUEST = "dyy_command_request_data";

    private static RedisHandler redisHandler;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String vin = session.getUri().toString().split("vin=")[1];
        if (vin != null) {
            devices.put(vin, session);
            LOGGER.info("{} webSocket console open",vin);
            //通知网关开始监控
            CommandDownRequest request = new CommandDownRequest(vin, OPEN_DEBUG);
            redisHandler.getRedisTemplateByType(LibraryType.COMMAND).convertAndSend(SRM_COMMAND_REQUEST, JSONObject.toJSONString(request));
            LOGGER.info("{} send gateway open debug",vin);
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String payload = (String)message.getPayload();
        LOGGER.debug("receive websocket message:" + payload);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        LOGGER.error("connect error", exception);
        devices.remove(getDeviceId(session));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        String vin = getDeviceId(session);
        devices.remove(getDeviceId(session));
        LOGGER.info("{} webSocket console close",vin);
        //通知网关停止监控
        CommandDownRequest request = new CommandDownRequest(vin, CLOSE_DEBUG);
        redisHandler.getRedisTemplateByType(LibraryType.COMMAND).convertAndSend(SRM_COMMAND_REQUEST, JSONObject.toJSONString(request));
        LOGGER.info("{} send gateway close debug",vin);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 发送信息给指定设备控制台
     * @param imei
     * @param message
     * @return
     */
    public void sendMessage(String imei, TextMessage message) {
        if (devices.get(imei) == null) {
            return ;
        }
        WebSocketSession session = devices.get(imei);
        if (!session.isOpen()) {
            return;
        }
        try {
            session.sendMessage(message);
        } catch (IOException e) {
            LOGGER.error("sendMessage error", e);
        }
    }

    /**
     * 广播信息给所有监控
     * @param message
     * @return
     */
    public void sendMessage(TextMessage message) {
        Set<String> imeis = devices.keySet();
        WebSocketSession session = null;
        for (String imei : imeis) {
            try {
                session = devices.get(imei);
                if (session.isOpen()) {
                    session.sendMessage(message);
                }
            } catch (IOException e) {
                LOGGER.error("sendMessage error", e);
            }
        }
    }

    /**
     * 获取设备标识
     * @param session
     * @return
     */
    public String getDeviceId(WebSocketSession session) {
        String deviceid = (String) session.getAttributes().get("WEBSOCKET_DEVICEID");
        return deviceid;
    }

    public Map<String, WebSocketSession> getDevices() {
        return devices;
    }

    public RedisHandler getRedisHandler() {
        return redisHandler;
    }

    @Autowired
    public void setRedisHandler(RedisHandler redisHandler) {
        this.redisHandler = redisHandler;
    }

}
