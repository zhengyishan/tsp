package com.dyy.tsp.core.evgb.entity;

import com.dyy.tsp.common.exception.BaseException;
import com.dyy.tsp.core.base.IStatus;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * 子系统电压数据
 */
@SuppressWarnings("all")
public class SubsystemVoltageData implements IStatus {

    /**
     * 子系统号
     */
    private Short num;

    /**
     * 电压
     */
    private Integer voltage;

    /**
     * 电流
     */
    private Integer current;

    /**
     * 单体电池个数
     */
    private Integer cellCount;

    /**
     * 本帧起始电池序号
     */
    private Integer batteryNumber;

    /**
     * 本帧单体电池总数
     */
    private Short batteryConnt;

    /**
     * 单体电池电压列表
     */
    private List<Integer> cellVoltages;


    @Override
    public SubsystemVoltageData decode(ByteBuf byteBuf) throws BaseException {
        SubsystemVoltageData subsystemVoltageData = new SubsystemVoltageData();
        subsystemVoltageData.setNum(byteBuf.readUnsignedByte());
        subsystemVoltageData.setVoltage(byteBuf.readUnsignedShort());
        subsystemVoltageData.setCurrent(byteBuf.readUnsignedShort());
        subsystemVoltageData.setCellCount(byteBuf.readUnsignedShort());
        subsystemVoltageData.setBatteryNumber(byteBuf.readUnsignedShort());
        short i = byteBuf.readUnsignedByte();
        subsystemVoltageData.setBatteryConnt(i);
        if(i>0){
            List<Integer> list =new ArrayList();
            for (int j = 0; j < i; j++) {
                int i1 = byteBuf.readUnsignedShort();
                list.add(i1);
            }
            subsystemVoltageData.setCellVoltages(list);
        }
        return subsystemVoltageData;
    }

    @Override
    public ByteBuf encode() throws BaseException {
        ByteBuf buffer = Unpooled.buffer();
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.writeByte(num);
        buffer.writeShort(voltage);
        buffer.writeShort(current);
        buffer.writeShort(cellCount);
        buffer.writeShort(batteryNumber);
        buffer.writeByte(batteryConnt);
        for (int i = 0; i < batteryConnt; i++) {
            buffer.writeShort(cellVoltages.get(i));
        }
        return buffer;
    }

    public Short getNum() {
        return num;
    }

    public void setNum(Short num) {
        this.num = num;
    }

    public Integer getVoltage() {
        return voltage;
    }

    public void setVoltage(Integer voltage) {
        this.voltage = voltage;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getCellCount() {
        return cellCount;
    }

    public void setCellCount(Integer cellCount) {
        this.cellCount = cellCount;
    }

    public Integer getBatteryNumber() {
        return batteryNumber;
    }

    public void setBatteryNumber(Integer batteryNumber) {
        this.batteryNumber = batteryNumber;
    }

    public Short getBatteryConnt() {
        return batteryConnt;
    }

    public void setBatteryConnt(Short batteryConnt) {
        this.batteryConnt = batteryConnt;
    }

    public List<Integer> getCellVoltages() {
        return cellVoltages;
    }

    public void setCellVoltages(List<Integer> cellVoltages) {
        this.cellVoltages = cellVoltages;
    }
}
