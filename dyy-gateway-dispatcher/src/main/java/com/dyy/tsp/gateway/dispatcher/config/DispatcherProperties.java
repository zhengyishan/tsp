package com.dyy.tsp.gateway.dispatcher.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = DispatcherProperties.PREFIX)
public class DispatcherProperties {

    public static final String PREFIX = "dispatcher";

    private String kafkaAcks = "1";

    private String kafkaLinger = "50";

    private String kafkaRetries = "1";

    private String kafkaRetryBackoff = "2000";

    private String kafkaReconnectBackoff = "3000";

    private String kafkaCompressionType = "gzip";

    //处理车辆信号
    private String statusTopic = "dyy_status_data";

    //处理故障
    private String faultTopic = "dyy_fault_data";

    //处理报警
    private String warningTopic = "dyy_warning_data";

    //处理位置
    private String locationTopic = "dyy_location_data";

    public String getKafkaAcks() {
        return kafkaAcks;
    }

    public void setKafkaAcks(String kafkaAcks) {
        this.kafkaAcks = kafkaAcks;
    }

    public String getKafkaLinger() {
        return kafkaLinger;
    }

    public void setKafkaLinger(String kafkaLinger) {
        this.kafkaLinger = kafkaLinger;
    }

    public String getKafkaRetries() {
        return kafkaRetries;
    }

    public void setKafkaRetries(String kafkaRetries) {
        this.kafkaRetries = kafkaRetries;
    }

    public String getKafkaRetryBackoff() {
        return kafkaRetryBackoff;
    }

    public void setKafkaRetryBackoff(String kafkaRetryBackoff) {
        this.kafkaRetryBackoff = kafkaRetryBackoff;
    }

    public String getKafkaReconnectBackoff() {
        return kafkaReconnectBackoff;
    }

    public void setKafkaReconnectBackoff(String kafkaReconnectBackoff) {
        this.kafkaReconnectBackoff = kafkaReconnectBackoff;
    }

    public String getKafkaCompressionType() {
        return kafkaCompressionType;
    }

    public void setKafkaCompressionType(String kafkaCompressionType) {
        this.kafkaCompressionType = kafkaCompressionType;
    }

    public String getStatusTopic() {
        return statusTopic;
    }

    public void setStatusTopic(String statusTopic) {
        this.statusTopic = statusTopic;
    }

    public String getFaultTopic() {
        return faultTopic;
    }

    public void setFaultTopic(String faultTopic) {
        this.faultTopic = faultTopic;
    }

    public String getWarningTopic() {
        return warningTopic;
    }

    public void setWarningTopic(String warningTopic) {
        this.warningTopic = warningTopic;
    }

    public String getLocationTopic() {
        return locationTopic;
    }

    public void setLocationTopic(String locationTopic) {
        this.locationTopic = locationTopic;
    }
}
