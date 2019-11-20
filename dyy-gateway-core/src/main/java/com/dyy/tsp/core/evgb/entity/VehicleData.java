package com.dyy.tsp.core.evgb.entity;

import com.dyy.tsp.common.exception.BaseException;
import com.dyy.tsp.core.base.IStatus;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.ByteOrder;

/**
 * 整车数据
 */
@SuppressWarnings("all")
public class VehicleData implements IStatus {

    /**
     * 车辆状态
     */
    private Short vehicleStatus;

    /**
     * 充电状态
     */
    private Short chargeStatus;

    /**
     * 运行模式
     */
    private Short operationMode;

    /**
     * 车速
     */
    private Integer speed;

    /**
     * 累计里程
     */
    private Long mileage;

    /**
     * 总电压
     */
    private Integer totalVoltage;

    /**
     * 总电流
     */
    private Integer totalCurrent;

    /**
     * SOC电量
     */
    private Short soc;

    /**
     * DC_DC状态
     */
    private Short dcStatus;

    /**
     * 挡位
     */
    private Short gears;

    /**
     * 绝缘电阻
     */
    private Integer insulationResistance;

    /**
     * 加速行程值
     */
    private Short accelerationValue;

    /**
     * 制动踏板状态
     */
    private Short brakePedalCondition;

    public Short getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(Short vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public Short getChargeStatus() {
        return chargeStatus;
    }

    public void setChargeStatus(Short chargeStatus) {
        this.chargeStatus = chargeStatus;
    }

    public Short getOperationMode() {
        return operationMode;
    }

    public void setOperationMode(Short operationMode) {
        this.operationMode = operationMode;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Long getMileage() {
        return mileage;
    }

    public void setMileage(Long mileage) {
        this.mileage = mileage;
    }

    public Integer getTotalVoltage() {
        return totalVoltage;
    }

    public void setTotalVoltage(Integer totalVoltage) {
        this.totalVoltage = totalVoltage;
    }

    public Integer getTotalCurrent() {
        return totalCurrent;
    }

    public void setTotalCurrent(Integer totalCurrent) {
        this.totalCurrent = totalCurrent;
    }

    public Short getSoc() {
        return soc;
    }

    public void setSoc(Short soc) {
        this.soc = soc;
    }

    public Short getDcStatus() {
        return dcStatus;
    }

    public void setDcStatus(Short dcStatus) {
        this.dcStatus = dcStatus;
    }

    public Short getGears() {
        return gears;
    }

    public void setGears(Short gears) {
        this.gears = gears;
    }

    public Integer getInsulationResistance() {
        return insulationResistance;
    }

    public void setInsulationResistance(Integer insulationResistance) {
        this.insulationResistance = insulationResistance;
    }

    public Short getAccelerationValue() {
        return accelerationValue;
    }

    public void setAccelerationValue(Short accelerationValue) {
        this.accelerationValue = accelerationValue;
    }

    public Short getBrakePedalCondition() {
        return brakePedalCondition;
    }

    public void setBrakePedalCondition(Short brakePedalCondition) {
        this.brakePedalCondition = brakePedalCondition;
    }

    @Override
    public VehicleData decode(ByteBuf byteBuf) throws BaseException {
        VehicleData vehicleData = new VehicleData();
        //车辆状态
        vehicleData.setVehicleStatus(byteBuf.readUnsignedByte());
        //充电状态
        vehicleData.setChargeStatus(byteBuf.readUnsignedByte());
        //运行模式
        vehicleData.setOperationMode(byteBuf.readUnsignedByte());
        //车速
        vehicleData.setSpeed(byteBuf.readUnsignedShort());
        //累计里程
        vehicleData.setMileage(byteBuf.readUnsignedInt());
        //总电压
        vehicleData.setTotalVoltage(byteBuf.readUnsignedShort());
        //总电流
        vehicleData.setTotalCurrent(byteBuf.readUnsignedShort());
        //SOC电量
        vehicleData.setSoc(byteBuf.readUnsignedByte());
        //DC-DC状态
        vehicleData.setDcStatus(byteBuf.readUnsignedByte());
        //档位
        vehicleData.setGears(byteBuf.readUnsignedByte());
        //绝缘电阻
        vehicleData.setInsulationResistance(byteBuf.readUnsignedShort());
        //加速行程值
        vehicleData.setAccelerationValue(byteBuf.readUnsignedByte());
        //制动踏板状态
        vehicleData.setBrakePedalCondition(byteBuf.readUnsignedByte());
        return vehicleData;
    }

    @Override
    public ByteBuf encode() throws BaseException{
        ByteBuf buffer = Unpooled.buffer();
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.writeByte(vehicleStatus);
        buffer.writeByte(chargeStatus);
        buffer.writeByte(operationMode);
        buffer.writeShort(speed);
        buffer.writeInt(mileage.intValue());
        buffer.writeShort(totalVoltage);
        buffer.writeShort(totalCurrent);
        buffer.writeByte(soc);
        buffer.writeByte(dcStatus);
        buffer.writeByte(gears);
        buffer.writeShort(insulationResistance);
        buffer.writeByte(accelerationValue);
        buffer.writeByte(accelerationValue);
        return buffer;
    }
}
