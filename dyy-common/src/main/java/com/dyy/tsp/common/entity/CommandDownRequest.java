package com.dyy.tsp.common.entity;

/**
 * 指令下发请求
 */
@SuppressWarnings("all")
public class CommandDownRequest {

    /**
     * 车架号
     */
    private String vin;

    /**
     * 指令类型
     */
    private String command;

    /**
     * 指令请求时间
     */
    private Long time;

    /**
     * 指令请求流水号
     */
    private Integer serialNum;

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(Integer serialNum) {
        this.serialNum = serialNum;
    }

    public CommandDownRequest() {
    }

    public CommandDownRequest(String vin, String command) {
        this.vin = vin;
        this.command = command;
    }
}
