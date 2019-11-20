package com.dyy.tsp.gateway.server.kafka;

import com.dyy.tsp.gateway.server.config.EvGBProperties;
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
public class KafkaCallBackProducer{

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaCallBackProducer.class);

    private KafkaProducer kafkaProducer;

    @Autowired
    private EvGBProperties evGBProperties;

    @Autowired
    private KafkaProperties kafkaProperties;

    @PostConstruct
    private void init(){
        if(StringUtils.isNotBlank(evGBProperties.getKafkaAcks())){
            this.kafkaProperties.getProperties().put("acks",evGBProperties.getKafkaAcks());
        }
        if(StringUtils.isNotBlank(evGBProperties.getKafkaLinger())){
            this.kafkaProperties.getProperties().put("linger.ms",evGBProperties.getKafkaLinger());
        }
        if(StringUtils.isNotBlank(evGBProperties.getKafkaRetries())){
            this.kafkaProperties.getProperties().put("retries",evGBProperties.getKafkaRetries());
        }
        if(StringUtils.isNotBlank(evGBProperties.getKafkaRetryBackoff())){
            this.kafkaProperties.getProperties().put("retry.backoff.ms",evGBProperties.getKafkaRetryBackoff());
        }
        if(StringUtils.isNotBlank(evGBProperties.getKafkaReconnectBackoff())){
            this.kafkaProperties.getProperties().put("reconnect.backoff.ms",evGBProperties.getKafkaReconnectBackoff());
        }
        if(StringUtils.isNotBlank(evGBProperties.getKafkaCompressionType())){
            this.kafkaProperties.getProperties().put("compression.type",evGBProperties.getKafkaCompressionType());
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
