package com.dyy.tsp.gateway.tcu.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@SuppressWarnings("all")
@ConfigurationProperties(prefix = TcuProperties.PREFIX)
public class TcuProperties {

    public static final String PREFIX = "tcu";

    //网关TCP域名或者IP
    private String gatewayHost = "localhost";

    //网关TCP端口
    private Integer gatewayPort = 8111;

    //Tbox终端应该设置4秒1次心跳。这里TCP模拟就设置30秒
    private Integer timeout = 30;

    //最大重连次数
    private Integer reconnectMaxNum = 10000;

    //心跳模拟车辆
    private String heartVin;

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

    public String getGatewayHost() {
        return gatewayHost;
    }

    public void setGatewayHost(String gatewayHost) {
        this.gatewayHost = gatewayHost;
    }

    public Integer getGatewayPort() {
        return gatewayPort;
    }

    public void setGatewayPort(Integer gatewayPort) {
        this.gatewayPort = gatewayPort;
    }

    public String getHeartVin() {
        return heartVin;
    }

    public void setHeartVin(String heartVin) {
        this.heartVin = heartVin;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
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

    public String getCommandRequestTopic() {
        return commandRequestTopic;
    }

    public void setCommandRequestTopic(String commandRequestTopic) {
        this.commandRequestTopic = commandRequestTopic;
    }

    public Integer getReconnectMaxNum() {
        return reconnectMaxNum;
    }

    public void setReconnectMaxNum(Integer reconnectMaxNum) {
        this.reconnectMaxNum = reconnectMaxNum;
    }
}
