package com.dyy.tsp.gateway.dispatcher.kafka;

import com.dyy.tsp.gateway.dispatcher.config.DispatcherProperties;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * kafka发送者。异步回调日志
 * created by dyy
 */
@Service
@SuppressWarnings("all")
public class KafkaCallBackProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaCallBackProducer.class);

    private KafkaProducer kafkaProducer;

    @Autowired
    private DispatcherProperties dispatcherProperties;

    @Autowired
    private KafkaProperties kafkaProperties;

    @PostConstruct
    private void init(){
        if(StringUtils.isNotBlank(dispatcherProperties.getKafkaAcks())){
            this.kafkaProperties.getProperties().put("acks",dispatcherProperties.getKafkaAcks());
        }
        if(StringUtils.isNotBlank(dispatcherProperties.getKafkaLinger())){
            this.kafkaProperties.getProperties().put("linger.ms",dispatcherProperties.getKafkaLinger());
        }
        if(StringUtils.isNotBlank(dispatcherProperties.getKafkaRetries())){
            this.kafkaProperties.getProperties().put("retries",dispatcherProperties.getKafkaRetries());
        }
        if(StringUtils.isNotBlank(dispatcherProperties.getKafkaRetryBackoff())){
            this.kafkaProperties.getProperties().put("retry.backoff.ms",dispatcherProperties.getKafkaRetryBackoff());
        }
        if(StringUtils.isNotBlank(dispatcherProperties.getKafkaReconnectBackoff())){
            this.kafkaProperties.getProperties().put("reconnect.backoff.ms",dispatcherProperties.getKafkaReconnectBackoff());
        }
        if(StringUtils.isNotBlank(dispatcherProperties.getKafkaCompressionType())){
            this.kafkaProperties.getProperties().put("compression.type",dispatcherProperties.getKafkaCompressionType());
        }
        kafkaProducer = new KafkaProducer(this.kafkaProperties.buildProducerProperties());
    }

    @PreDestroy
    public void stop() {
        if(kafkaProducer!=null){
            kafkaProducer.close();
        }
    }

    /**
     * 发送消息
     * @param topic
     * @param message
     */
    public void send(String topic,String message){
        kafkaProducer.send(new ProducerRecord(topic,message), new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if(e != null){
                    e.printStackTrace();
                }else{
                    LOGGER.trace("topic[{}] partition[{}] offset[{}]",recordMetadata.topic(),recordMetadata.partition(),recordMetadata.offset());
                }
            }
        });
    }

    public KafkaProducer getKafkaProducer() {
        return kafkaProducer;
    }

    public void setKafkaProducer(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }
}
