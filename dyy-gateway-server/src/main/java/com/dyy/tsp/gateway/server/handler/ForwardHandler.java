package com.dyy.tsp.gateway.server.handler;

import com.alibaba.fastjson.JSONObject;
import com.dyy.tsp.core.evgb.entity.EvGBProtocol;
import com.dyy.tsp.gateway.server.config.EvGBProperties;
import com.dyy.tsp.gateway.server.kafka.KafkaCallBackProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 数据转发处理器
 * created by dyy
 */
@Service
@SuppressWarnings("all")
public class ForwardHandler {

    @Autowired
    private KafkaCallBackProducer kafkaCallBackProducer;

    @Autowired
    private EvGBProperties evGBProperties;

    /**
     * 转发数据到Debug
     * @param protocol
     */
    public void sendToDebug(EvGBProtocol protocol){
        protocol.setGatewayForwardTime(System.currentTimeMillis());
        kafkaCallBackProducer.send(evGBProperties.getDebugTopic(), JSONObject.toJSONString(protocol));
    }

    /**
     * 转发数据到Dispatcher
     * @param protocol
     */
    public void sendToDispatcher(EvGBProtocol protocol){
        protocol.setGatewayForwardTime(System.currentTimeMillis());
        kafkaCallBackProducer.send(evGBProperties.getDispatcherTopic(),JSONObject.toJSONString(protocol));
    }

}
