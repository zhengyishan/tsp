package com.dyy.tsp.gateway.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@SuppressWarnings("all")
@ConfigurationProperties(prefix = EvGBProperties.PREFIX)
public class EvGBProperties {

    public static final String PREFIX = "evgb";

    //TCP网关端口
    private Integer port = 8111;

    //心跳空闲检查时间90秒
    private Integer timeout = 90;

    //三次握手队列最大长度
    private Integer soBackLog = 1024;

    private Integer maxFrameLength = 65556;

    private Integer lengthFieldOffset = 22;

    private Integer lengthFieldLength = 2;

    private Integer lengthAdjustment = 1;

    private Integer initialBytesToStrip = 0 ;

    private Boolean failFast = Boolean.TRUE;

    private String kafkaAcks = "1";

    private String kafkaLinger = "50";

    private String kafkaRetries = "1";

    private String kafkaRetryBackoff = "2000";

    private String kafkaReconnectBackoff = "3000";

    private String kafkaCompressionType = "gzip";

    //指令下发请求
    private String commandRequestTopic = "dyy_command_request_data";

    //指令下发响应
    private String commandResponseTopic = "dyy_command_response_data";

    //在线监控
    private String debugTopic = "dyy_debug_data";

    //dispatcher处理
    private String dispatcherTopic = "dyy_dispatcher_data";

    //转发国标/地标
    private String evgbTopic = "dyy_evgb_data";

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getSoBackLog() {
        return soBackLog;
    }

    public void setSoBackLog(Integer soBackLog) {
        this.soBackLog = soBackLog;
    }

    public Integer getMaxFrameLength() {
        return maxFrameLength;
    }

    public void setMaxFrameLength(Integer maxFrameLength) {
        this.maxFrameLength = maxFrameLength;
    }

    public Integer getLengthFieldOffset() {
        return lengthFieldOffset;
    }

    public void setLengthFieldOffset(Integer lengthFieldOffset) {
        this.lengthFieldOffset = lengthFieldOffset;
    }

    public Integer getLengthFieldLength() {
        return lengthFieldLength;
    }

    public void setLengthFieldLength(Integer lengthFieldLength) {
        this.lengthFieldLength = lengthFieldLength;
    }

    public Integer getLengthAdjustment() {
        return lengthAdjustment;
    }

    public void setLengthAdjustment(Integer lengthAdjustment) {
        this.lengthAdjustment = lengthAdjustment;
    }

    public Integer getInitialBytesToStrip() {
        return initialBytesToStrip;
    }

    public void setInitialBytesToStrip(Integer initialBytesToStrip) {
        this.initialBytesToStrip = initialBytesToStrip;
    }

    public Boolean getFailFast() {
        return failFast;
    }

    public void setFailFast(Boolean failFast) {
        this.failFast = failFast;
    }

    public String getDebugTopic() {
        return debugTopic;
    }

    public void setDebugTopic(String debugTopic) {
        this.debugTopic = debugTopic;
    }

    public String getDispatcherTopic() {
        return dispatcherTopic;
    }

    public void setDispatcherTopic(String dispatcherTopic) {
        this.dispatcherTopic = dispatcherTopic;
    }

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

    public String getEvgbTopic() {
        return evgbTopic;
    }

    public void setEvgbTopic(String evgbTopic) {
        this.evgbTopic = evgbTopic;
    }

    public String getCommandRequestTopic() {
        return commandRequestTopic;
    }

    public void setCommandRequestTopic(String commandRequestTopic) {
        this.commandRequestTopic = commandRequestTopic;
    }

    public String getCommandResponseTopic() {
        return commandResponseTopic;
    }

    public void setCommandResponseTopic(String commandResponseTopic) {
        this.commandResponseTopic = commandResponseTopic;
    }
}
