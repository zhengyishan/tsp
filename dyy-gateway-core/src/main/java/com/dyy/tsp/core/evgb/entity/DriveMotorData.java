package com.dyy.tsp.core.evgb.entity;

import com.dyy.tsp.common.exception.BaseException;
import com.dyy.tsp.core.base.IStatus;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.ByteOrder;

/**
 * 驱动电机数据
 */
@SuppressWarnings("all")
public class DriveMotorData implements IStatus {

    /**
     * 序号
     */
    private Short num;

    /**
     * 驱动电机状态
     */
    private Short status;

    /**
     * 驱动电机控制器温度
     */
    private Short controllerTemperature;

    /**
     * 驱动电机转速
     */
    private Integer speed;

    /**
     * 驱动电机转矩
     */
    private Integer torque;

    /**
     * 驱动电机温度
     */
    private Short temperature;

    /**
     * 驱动电机控制器输入电压
     */
    private Integer controllerInputVoltage;

    /**
     * 驱动电机控制器直流母线电流
     */
    private Integer controllerDcBusbarCurrent;

    public Short getNum() {
        return num;
    }

    public void setNum(Short num) {
        this.num = num;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getControllerTemperature() {
        return controllerTemperature;
    }

    public void setControllerTemperature(Short controllerTemperature) {
        this.controllerTemperature = controllerTemperature;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getTorque() {
        return torque;
    }

    public void setTorque(Integer torque) {
        this.torque = torque;
    }

    public Short getTemperature() {
        return temperature;
    }

    public void setTemperature(Short temperature) {
        this.temperature = temperature;
    }

    public Integer getControllerInputVoltage() {
        return controllerInputVoltage;
    }

    public void setControllerInputVoltage(Integer controllerInputVoltage) {
        this.controllerInputVoltage = controllerInputVoltage;
    }

    public Integer getControllerDcBusbarCurrent() {
        return controllerDcBusbarCurrent;
    }

    public void setControllerDcBusbarCurrent(Integer controllerDcBusbarCurrent) {
        this.controllerDcBusbarCurrent = controllerDcBusbarCurrent;
    }

    @Override
    public DriveMotorData decode(ByteBuf byteBuf) throws BaseException {
        DriveMotorData driveMotorData = new DriveMotorData();
        driveMotorData.setNum(byteBuf.readUnsignedByte());
        driveMotorData.setStatus(byteBuf.readUnsignedByte());
        driveMotorData.setControllerTemperature(byteBuf.readUnsignedByte());
        driveMotorData.setSpeed(byteBuf.readUnsignedShort());
        driveMotorData.setTorque(byteBuf.readUnsignedShort());
        driveMotorData.setTemperature(byteBuf.readUnsignedByte());
        driveMotorData.setControllerInputVoltage(byteBuf.readUnsignedShort());
        driveMotorData.setControllerDcBusbarCurrent(byteBuf.readUnsignedShort());
        return driveMotorData;
    }

    @Override
    public ByteBuf encode() throws BaseException {
        ByteBuf buffer = Unpooled.buffer();
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.writeByte(num);
        buffer.writeByte(status);
        buffer.writeByte(controllerTemperature);
        buffer.writeShort(speed);
        buffer.writeShort(torque);
        buffer.writeByte(temperature);
        buffer.writeShort(controllerInputVoltage);
        buffer.writeShort(controllerDcBusbarCurrent);
        return buffer;
    }

}
