package com.dyy.tsp.autoconfig.kafka;

import com.dyy.tsp.common.kafka.AbstractListener;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.CollectionUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaProperties {
    private List<String> bootstrapServers;
    private final Map<String, Consumer> consumers = new HashMap<>();
    private final Producer producer = new Producer();

    private Map<String, String> properties = new HashMap<>();

    public List<String> getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(List<String> bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public Map<String, Consumer> getConsumers() {
        return consumers;
    }

    public Producer getProducer() {
        return producer;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    private Map<String, Object> buildCommonProperties() {
        Map<String, Object> properties = new HashMap<String, Object>();
        if (this.bootstrapServers != null) {
            properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG,
                    this.bootstrapServers);
        }
        if (!CollectionUtils.isEmpty(this.properties)) {
            properties.putAll(this.properties);
        }
        return properties;
    }


    public Map<String, Object> buildConsumerProperties(Consumer consumer) {
        Map<String, Object> properties = buildCommonProperties();
        properties.putAll(consumer.buildProperties());
        return properties;
    }

    public Map<String, Object> buildProducerProperties() {
        Map<String, Object> properties = buildCommonProperties();
        properties.putAll(this.producer.buildProperties());
        return properties;
    }

    public static class Consumer {
        private List<String> bootstrapServers;
        private String topic;
        private String groupId;

        private Boolean enableAutoCommit;

        private Class<? extends Deserializer> keyDeserializer = StringDeserializer.class;

        private Class<? extends Deserializer> valueDeserializer = StringDeserializer.class;
        private Integer maxPollRecords;
        private Integer taskNum = 1;

        private Class<? extends AbstractListener> listener;

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public List<String> getBootstrapServers() {
            return this.bootstrapServers;
        }

        public void setBootstrapServers(List<String> bootstrapServers) {
            this.bootstrapServers = bootstrapServers;
        }


        public Boolean getEnableAutoCommit() {
            return this.enableAutoCommit;
        }

        public void setEnableAutoCommit(Boolean enableAutoCommit) {
            this.enableAutoCommit = enableAutoCommit;
        }

        public Class<? extends Deserializer> getKeyDeserializer() {
            return keyDeserializer;
        }

        public void setKeyDeserializer(Class<? extends Deserializer> keyDeserializer) {
            this.keyDeserializer = keyDeserializer;
        }

        public Class<? extends Deserializer> getValueDeserializer() {
            return valueDeserializer;
        }

        public void setValueDeserializer(Class<? extends Deserializer> valueDeserializer) {
            this.valueDeserializer = valueDeserializer;
        }

        public Integer getMaxPollRecords() {
            return this.maxPollRecords;
        }

        public void setMaxPollRecords(Integer maxPollRecords) {
            this.maxPollRecords = maxPollRecords;
        }

        public Integer getTaskNum() {
            return taskNum;
        }

        public void setTaskNum(Integer taskNum) {
            this.taskNum = taskNum;
        }

        public Class<? extends AbstractListener> getListener() {
            return listener;
        }

        public void setListener(Class<? extends AbstractListener> listener) {
            this.listener = listener;
        }

        public Map<String, Object> buildProperties() {
            Map<String, Object> properties = new HashMap<String, Object>();
            if (this.bootstrapServers != null) {
                properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                        this.bootstrapServers);
            }
            if (this.groupId != null) {
                properties.put(ConsumerConfig.GROUP_ID_CONFIG, this.groupId);
            }
            if (this.enableAutoCommit != null) {
                properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,
                        this.enableAutoCommit);
            }
            if (this.keyDeserializer != null) {
                properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                        this.keyDeserializer);
            }
            if (this.valueDeserializer != null) {
                properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                        this.valueDeserializer);
            }
            if (this.maxPollRecords != null) {
                properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,
                        this.maxPollRecords);
            }
            return properties;
        }

    }

    public static class Producer {

        private List<String> bootstrapServers;

        private String topic;

        private Class<? extends Serializer> keySerializer = StringSerializer.class;

        private Class<? extends Serializer> valueSerializer = StringSerializer.class;


        public List<String> getBootstrapServers() {
            return this.bootstrapServers;
        }

        public void setBootstrapServers(List<String> bootstrapServers) {
            this.bootstrapServers = bootstrapServers;
        }

        public Class<? extends Serializer> getKeySerializer() {
            return keySerializer;
        }

        public void setKeySerializer(Class<? extends Serializer> keySerializer) {
            this.keySerializer = keySerializer;
        }

        public Class<? extends Serializer> getValueSerializer() {
            return valueSerializer;
        }

        public void setValueSerializer(Class<? extends Serializer> valueSerializer) {
            this.valueSerializer = valueSerializer;
        }

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public Map<String, Object> buildProperties() {
            Map<String, Object> properties = new HashMap<String, Object>();
            if (this.bootstrapServers != null) {
                properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                        this.bootstrapServers);
            }
            if (this.keySerializer != null) {
                properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                        this.keySerializer);
            }
            if (this.valueSerializer != null) {
                properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                        this.valueSerializer);
            }
            return properties;
        }

    }
}
