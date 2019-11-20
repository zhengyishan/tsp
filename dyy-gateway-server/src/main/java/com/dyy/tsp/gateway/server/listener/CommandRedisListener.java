package com.dyy.tsp.gateway.server.listener;

import com.dyy.tsp.gateway.server.config.EvGBProperties;
import com.dyy.tsp.gateway.server.config.RedisConfig;
import com.dyy.tsp.gateway.server.handler.CommandDownHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Service;

/**
 * 基于Redis订阅发布机制实现网关多节点消息广播
 * Redis更合适与K8S的pod多副本部署，不像Kafka需要配置不同的GroupId
 * created by dyy
 */
@Service
@Configuration
@SuppressWarnings("all")
public class CommandRedisListener {

    @Value("${spring.redis.database.command}")
    private int commandDataBase;

    @Autowired
    private RedisConfig redisConfig;

    @Autowired
    private EvGBProperties evGBProperties;

    /**
     * redis消息监听器容器
     * @param listenerAdapter
     * @return
     */
    @Bean
    public RedisMessageListenerContainer container(@Qualifier("listenerAdapter") MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        RedisConnectionFactory rf = redisConfig.connectionFactory(
                redisConfig.getHostName(),redisConfig.getPort(),redisConfig.getPassword(),redisConfig.getMaxIdle(),
                redisConfig.getMaxTotal(),commandDataBase,redisConfig.getMaxWaitMillis(),redisConfig.getTestOnBorrow());
        container.setConnectionFactory(rf);
        container.addMessageListener(listenerAdapter, new PatternTopic(evGBProperties.getCommandRequestTopic()));
        return container;
    }

    /**
     * 消息监听器适配器，绑定消息处理器，利用反射技术调用消息处理器的业务方法
     * @param handler
     * @return
     */
    @Bean("listenerAdapter")
    public MessageListenerAdapter listenerAdapter(CommandDownHandler handler) {
        return new MessageListenerAdapter(handler, "doCommandDown");
    }

}
