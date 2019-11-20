package com.dyy.tsp.core.evgb.entity;

import com.dyy.tsp.common.exception.BaseException;
import com.dyy.tsp.core.base.IStatus;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.ByteOrder;

/**
 * 车辆登出
 */
@SuppressWarnings("all")
public class VehicleLogout implements IStatus {

    private static final BeanTime producer = new BeanTime();

    //车辆登出时间
    private BeanTime beanTime;

    //车辆登出流水号
    private Integer serialNum;

    @Override
    public VehicleLogout decode(ByteBuf byteBuf) throws BaseException {
        VehicleLogout vehicleLogout = new VehicleLogout();
        BeanTime beanTime = producer.decode(byteBuf);
        vehicleLogout.setBeanTime(beanTime);
        vehicleLogout.setSerialNum(byteBuf.readUnsignedShort());
        return vehicleLogout;
    }

    @Override
    public ByteBuf encode() throws BaseException {
        ByteBuf buffer = Unpooled.buffer();
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.writeBytes(beanTime.encode());
        buffer.writeShort(serialNum);
        return buffer;
    }

    public BeanTime getBeanTime() {
        return beanTime;
    }

    public void setBeanTime(BeanTime beanTime) {
        this.beanTime = beanTime;
    }

    public Integer getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(Integer serialNum) {
        this.serialNum = serialNum;
    }
}
