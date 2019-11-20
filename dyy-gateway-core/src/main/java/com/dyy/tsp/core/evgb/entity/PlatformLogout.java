package com.dyy.tsp.core.evgb.entity;

import com.dyy.tsp.common.exception.BaseException;
import com.dyy.tsp.core.base.IStatus;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.ByteOrder;

/**
 * 平台登出
 */
@SuppressWarnings("all")
public class PlatformLogout implements IStatus {

    private static final BeanTime producer = new BeanTime();

    //平台登出
    private BeanTime beanTime;

    //平台登出流水号
    private Integer serialNum;

    @Override
    public PlatformLogout decode(ByteBuf byteBuf) throws BaseException {
        PlatformLogout platformLogout = new PlatformLogout();
        BeanTime beanTime = producer.decode(byteBuf);
        platformLogout.setBeanTime(beanTime);
        platformLogout.setSerialNum(byteBuf.readUnsignedShort());
        return platformLogout;
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
