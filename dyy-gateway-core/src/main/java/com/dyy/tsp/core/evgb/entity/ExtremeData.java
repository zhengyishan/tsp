package com.dyy.tsp.core.evgb.entity;

import com.dyy.tsp.common.exception.BaseException;
import com.dyy.tsp.core.base.IStatus;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.ByteOrder;

/**
 * 极值数据
 */
@SuppressWarnings("all")
public class ExtremeData implements IStatus {

    /**
     * 最高电压电池子系统号
     */
    private Short maxVoltageSystemNum;

    /**
     * 最高电压单体电池代号
     */
    private Short maxVoltagebatteryNum;

    /**
     * 单体电池电压最高值
     */
    private Integer batteryMaxVoltage;

    /**
     * 最低电压电池子系统号
     */
    private Short minVoltageSystemNum;

    /**
     * 最低电压单体电池代号
     */
    private Short minVoltagebatteryNum;

    /**
     * 单体电池电压最低值
     */
    private Integer batteryMinVoltage;

    /**
     * 最高温度子系统号
     */
    private Short maxTemperatureSystemNum;

    /**
     * 最高温度探针序号
     */
    private Short maxTemperatureNum;

    /**
     * 最高温度值
     */
    private Short maxTemperature;

    /**
     * 最低温度子系统号
     */
    private Short minTemperatureSystemNum;

    /**
     * 最低温度探针序号
     */
    private Short minTemperatureNum;

    /**
     * 最低温度值
     */
    private Short minTemperature;

    public Short getMaxVoltageSystemNum() {
        return maxVoltageSystemNum;
    }

    public void setMaxVoltageSystemNum(Short maxVoltageSystemNum) {
        this.maxVoltageSystemNum = maxVoltageSystemNum;
    }

    public Short getMaxVoltagebatteryNum() {
        return maxVoltagebatteryNum;
    }

    public void setMaxVoltagebatteryNum(Short maxVoltagebatteryNum) {
        this.maxVoltagebatteryNum = maxVoltagebatteryNum;
    }

    public Integer getBatteryMaxVoltage() {
        return batteryMaxVoltage;
    }

    public void setBatteryMaxVoltage(Integer batteryMaxVoltage) {
        this.batteryMaxVoltage = batteryMaxVoltage;
    }

    public Short getMinVoltageSystemNum() {
        return minVoltageSystemNum;
    }

    public void setMinVoltageSystemNum(Short minVoltageSystemNum) {
        this.minVoltageSystemNum = minVoltageSystemNum;
    }

    public Short getMinVoltagebatteryNum() {
        return minVoltagebatteryNum;
    }

    public void setMinVoltagebatteryNum(Short minVoltagebatteryNum) {
        this.minVoltagebatteryNum = minVoltagebatteryNum;
    }

    public Integer getBatteryMinVoltage() {
        return batteryMinVoltage;
    }

    public void setBatteryMinVoltage(Integer batteryMinVoltage) {
        this.batteryMinVoltage = batteryMinVoltage;
    }

    public Short getMaxTemperatureSystemNum() {
        return maxTemperatureSystemNum;
    }

    public void setMaxTemperatureSystemNum(Short maxTemperatureSystemNum) {
        this.maxTemperatureSystemNum = maxTemperatureSystemNum;
    }

    public Short getMaxTemperatureNum() {
        return maxTemperatureNum;
    }

    public void setMaxTemperatureNum(Short maxTemperatureNum) {
        this.maxTemperatureNum = maxTemperatureNum;
    }

    public Short getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Short maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public Short getMinTemperatureSystemNum() {
        return minTemperatureSystemNum;
    }

    public void setMinTemperatureSystemNum(Short minTemperatureSystemNum) {
        this.minTemperatureSystemNum = minTemperatureSystemNum;
    }

    public Short getMinTemperatureNum() {
        return minTemperatureNum;
    }

    public void setMinTemperatureNum(Short minTemperatureNum) {
        this.minTemperatureNum = minTemperatureNum;
    }

    public Short getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(Short minTemperature) {
        this.minTemperature = minTemperature;
    }

    @Override
    public ExtremeData decode(ByteBuf byteBuf) throws BaseException {
        ExtremeData extremeData = new ExtremeData();
        extremeData.setMaxVoltageSystemNum(byteBuf.readUnsignedByte());
        extremeData.setMaxVoltagebatteryNum(byteBuf.readUnsignedByte());
        extremeData.setBatteryMaxVoltage(byteBuf.readUnsignedShort());
        extremeData.setMinVoltageSystemNum(byteBuf.readUnsignedByte());
        extremeData.setMinVoltagebatteryNum(byteBuf.readUnsignedByte());
        extremeData.setBatteryMinVoltage(byteBuf.readUnsignedShort());
        extremeData.setMaxTemperatureSystemNum(byteBuf.readUnsignedByte());
        extremeData.setMaxTemperatureNum(byteBuf.readUnsignedByte());
        extremeData.setMaxTemperature(byteBuf.readUnsignedByte());
        extremeData.setMinTemperatureSystemNum(byteBuf.readUnsignedByte());
        extremeData.setMinTemperatureNum(byteBuf.readUnsignedByte());
        extremeData.setMinTemperature(byteBuf.readUnsignedByte());
        return extremeData;
    }

    @Override
    public ByteBuf encode() throws BaseException {
        ByteBuf buffer = Unpooled.buffer();
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.writeByte(maxVoltageSystemNum);
        buffer.writeByte(maxVoltagebatteryNum);
        buffer.writeShort(batteryMaxVoltage);
        buffer.writeByte(minVoltageSystemNum);
        buffer.writeByte(minVoltagebatteryNum);
        buffer.writeShort(batteryMinVoltage);
        buffer.writeByte(maxTemperatureSystemNum);
        buffer.writeByte(maxTemperatureNum);
        buffer.writeByte(maxTemperature);
        buffer.writeByte(minTemperatureSystemNum);
        buffer.writeByte(minTemperatureNum);
        buffer.writeByte(minTemperature);
        return buffer;
    }

}
