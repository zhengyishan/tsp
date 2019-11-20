package com.dyy.tsp.core.evgb.entity;

import com.dyy.tsp.common.constants.Constants;
import com.dyy.tsp.common.exception.BaseException;
import com.dyy.tsp.core.base.IStatus;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 车辆登入
 */
@SuppressWarnings("all")
public class VehicleLogin implements IStatus {

    private static final BeanTime producer = new BeanTime();

    //车辆登入时间
    private BeanTime beanTime;

    //车辆登入流水号
    private Integer serialNum;

    //ICCID 20位
    private String iccid;

    //可充电储能子系统个数
    private Short count;

    //可充电储能子系统编码长度
    private Short length;

    //可充电储能子系统编码集合
    private List<String> codes;

    @Override
    public VehicleLogin decode(ByteBuf byteBuf) throws BaseException {
        VehicleLogin vehicleLogin = new VehicleLogin();
        BeanTime beanTime = producer.decode(byteBuf);
        vehicleLogin.setBeanTime(beanTime);
        vehicleLogin.setSerialNum(byteBuf.readUnsignedShort());
        vehicleLogin.setIccid(byteBuf.readSlice(20).toString(Charset.forName(Constants.UTF_8)));
        vehicleLogin.setCount(byteBuf.readUnsignedByte());
        vehicleLogin.setLength(byteBuf.readUnsignedByte());
        if(vehicleLogin.getCount()>0 && vehicleLogin.getLength()>0){
            List<String> codeList = new ArrayList<>();
            for (int i = 0; i < vehicleLogin.getCount(); i++) {
                String code = byteBuf.readSlice(vehicleLogin.getLength()).toString(Charset.forName(Constants.UTF_8));
                codeList.add(code);
            }
            vehicleLogin.setCodes(codeList);
        }
        return vehicleLogin;
    }

    @Override
    public ByteBuf encode() throws BaseException {
        if(iccid.length()!=20){
            throw new BaseException("iccid length must be 20");
        }
        ByteBuf buffer = Unpooled.buffer();
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.writeBytes(beanTime.encode());
        buffer.writeShort(serialNum);
        buffer.writeBytes(iccid.getBytes(Charset.forName(Constants.UTF_8)));
        if(codes.isEmpty()){
            buffer.writeByte(0);
            buffer.writeByte(0);
        }else{
            buffer.writeByte(codes.size());
            buffer.writeByte(codes.get(0).getBytes().length);
            for (String code : codes) {
                buffer.writeBytes(code.getBytes(Charset.forName(Constants.UTF_8)));
            }
        }
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

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public Short getCount() {
        return count;
    }

    public void setCount(Short count) {
        this.count = count;
    }

    public Short getLength() {
        return length;
    }

    public void setLength(Short length) {
        this.length = length;
    }

    public List<String> getCodes() {
        return codes;
    }

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }
}
