package com.dyy.tsp.common.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import java.util.Arrays;
import java.util.Iterator;

public abstract class AbstractListener<K, V> implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(AbstractListener.class);
    private String topic;
    private KafkaConsumer consumer;
    protected ApplicationContext context;
    protected Boolean running = false;

    @Override
    public void run() {
        try {
            consumer.subscribe(Arrays.asList(topic));
            while (running) {
                try {
                    ConsumerRecords<K, V> records = consumer.poll(5 * 1000);
                    if (records == null || records.isEmpty()) {
                        continue;
                    }
                    Iterator<ConsumerRecord<K, V>> iterator = records.iterator();
                    while (running && iterator.hasNext()) {
                        ConsumerRecord<K, V> record = iterator.next();
                        V v = record.value();
                        listen(v);
                    }
                } catch (Throwable e) {
                    logger.error("listen error", e);
                }
            }
        } catch (Throwable e) {
            logger.error("listen error", e);
        } finally {
            consumer.close();
        }
    }

    public void init() {

    }

    protected abstract void listen(V v) throws Exception;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public KafkaConsumer getConsumer() {
        return consumer;
    }

    public void setConsumer(KafkaConsumer consumer) {
        this.consumer = consumer;
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }
}
