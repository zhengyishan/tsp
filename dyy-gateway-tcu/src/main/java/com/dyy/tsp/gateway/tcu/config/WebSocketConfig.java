package com.dyy.tsp.gateway.tcu.config;

import com.dyy.tsp.gateway.tcu.websocket.DebugWebSocketHandler;
import com.dyy.tsp.gateway.tcu.websocket.WebSocketInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * WebSocket配置类
 * created by dyy
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new DebugWebSocketHandler(), "/websocket/{vin}").setAllowedOrigins("*").addInterceptors(new WebSocketInterceptor());
    }
}
