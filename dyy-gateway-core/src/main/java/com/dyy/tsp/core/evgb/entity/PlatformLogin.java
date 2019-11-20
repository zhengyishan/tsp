package com.dyy.tsp.core.evgb.entity;

import com.dyy.tsp.common.constants.Constants;
import com.dyy.tsp.common.exception.BaseException;
import com.dyy.tsp.core.base.IStatus;
import com.dyy.tsp.core.evgb.enumtype.EncryptionType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

/**
 * 平台登入
 */
@SuppressWarnings("all")
public class PlatformLogin implements IStatus {

    private static final BeanTime producer = new BeanTime();

    //平台登入时间
    private BeanTime beanTime;

    //平台登入流水号
    private Integer serialNum;

    //平台登入用户名 12位
    private String userName;

    //平台登入用户名 20位
    private String password;

    //加密方式
    private EncryptionType encryptionType;

    @Override
    public PlatformLogin decode(ByteBuf byteBuf) throws BaseException {
        PlatformLogin platformLogin = new PlatformLogin();
        BeanTime beanTime = producer.decode(byteBuf);
        platformLogin.setBeanTime(beanTime);
        platformLogin.setSerialNum(byteBuf.readUnsignedShort());
        platformLogin.setUserName(byteBuf.readSlice(12).toString(Charset.forName(Constants.UTF_8)));
        platformLogin.setPassword(byteBuf.readSlice(20).toString(Charset.forName(Constants.UTF_8)));
        platformLogin.setEncryptionType(EncryptionType.valuesOf(byteBuf.readUnsignedByte()));
        return platformLogin;
    }

    @Override
    public ByteBuf encode() throws BaseException {
        if(userName.length()!=12){
            throw new BaseException("userName length must be 12");
        }
        if(password.length()!=20){
            throw new BaseException("password length must be 20");
        }
        ByteBuf buffer = Unpooled.buffer();
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.writeBytes(beanTime.encode());
        buffer.writeShort(serialNum);
        buffer.writeBytes(userName.getBytes(Charset.forName(Constants.UTF_8)));
        buffer.writeBytes(password.getBytes(Charset.forName(Constants.UTF_8)));
        buffer.writeByte(encryptionType.getId());
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EncryptionType getEncryptionType() {
        return encryptionType;
    }

    public void setEncryptionType(EncryptionType encryptionType) {
        this.encryptionType = encryptionType;
    }
}
