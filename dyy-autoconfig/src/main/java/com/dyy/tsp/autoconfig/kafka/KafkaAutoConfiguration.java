package com.dyy.tsp.autoconfig.kafka;

import com.dyy.tsp.common.exception.BaseException;
import com.dyy.tsp.common.kafka.AbstractListener;
import com.dyy.tsp.common.kafka.KafkaConsumerManager;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import javax.annotation.PreDestroy;
import java.util.Map;

@SuppressWarnings("all")
@Configuration
@ConditionalOnClass({KafkaProducer.class, KafkaConsumer.class})
@ConditionalOnProperty("spring.kafka.bootstrapServers")
@EnableConfigurationProperties(KafkaProperties.class)
public class KafkaAutoConfiguration implements ApplicationListener<ApplicationReadyEvent>, ApplicationContextAware, InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(KafkaAutoConfiguration.class);
    private ApplicationContext context;
    private KafkaProducer producer;
    @Autowired
    private KafkaProperties properties;
    @Autowired
    private KafkaConsumerManager consumerManager;

    @Bean
    public KafkaConsumerManager consumerManager() {
        return new KafkaConsumerManager();
    }

    @Bean
    @ConditionalOnMissingBean
    public KafkaProducer producer() {
        producer = new KafkaProducer(properties.buildProducerProperties());
        return producer;
    }

    @PreDestroy
    public void destory() {
        if (consumerManager != null) {
            consumerManager.shutdown();
        }
        if (producer != null) {
            producer.close();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, KafkaProperties.Consumer> consumers = properties.getConsumers();
        if (CollectionUtils.isEmpty(consumers)) {
            logger.debug("consumers is empty");
            return;
        }
        for (String key : consumers.keySet()) {
            KafkaProperties.Consumer consumer = consumers.get(key);
            Map<String, Object> props = properties.buildConsumerProperties(consumer);
            AbstractListener listener = consumer.getListener().newInstance();
            consumerManager.createConsumer(consumer.getTaskNum(), consumer.getTopic(), props, context, listener);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.context = context;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent){
        Map<String, KafkaProperties.Consumer> consumers = properties.getConsumers();
        if (CollectionUtils.isEmpty(consumers)) {
            logger.debug("consumers is empty");
            return;
        }
        for (String key : consumers.keySet()) {
            KafkaProperties.Consumer consumer = consumers.get(key);
            Map<String, Object> props = properties.buildConsumerProperties(consumer);
            AbstractListener listener = null;
            try {
                listener = consumer.getListener().newInstance();
                consumerManager.createConsumer(consumer.getTaskNum(), consumer.getTopic(), props, context, listener);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new BaseException(e.getMessage(), e);
            }
        }
    }
}
