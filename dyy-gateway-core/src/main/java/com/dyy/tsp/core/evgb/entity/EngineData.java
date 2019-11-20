package com.dyy.tsp.core.evgb.entity;

import com.dyy.tsp.common.exception.BaseException;
import com.dyy.tsp.core.base.IStatus;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.ByteOrder;

/**
 * 发动机数据
 */
@SuppressWarnings("all")
public class EngineData implements IStatus {

    /**
     * 发动机状态
     */
    private Short status;

    /**
     * 曲轴转速
     */
    private Integer crankshaftSpeed;

    /**
     * 燃料消耗率
     */
    private Integer fuelConsumption;

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Integer getCrankshaftSpeed() {
        return crankshaftSpeed;
    }

    public void setCrankshaftSpeed(Integer crankshaftSpeed) {
        this.crankshaftSpeed = crankshaftSpeed;
    }

    public Integer getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(Integer fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }


    @Override
    public EngineData decode(ByteBuf byteBuf) throws BaseException {
        EngineData engineData = new EngineData();
        engineData.setStatus(byteBuf.readUnsignedByte());
        engineData.setCrankshaftSpeed(byteBuf.readUnsignedShort());
        engineData.setFuelConsumption(byteBuf.readUnsignedShort());
        return engineData;
    }

    @Override
    public ByteBuf encode() throws BaseException {
        ByteBuf buffer = Unpooled.buffer();
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.writeByte(status);
        buffer.writeShort(crankshaftSpeed);
        buffer.writeShort(fuelConsumption);
        return buffer;
    }

}
