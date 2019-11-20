package com.dyy.tsp.core.evgb.entity;

import com.dyy.tsp.common.constants.Constants;
import com.dyy.tsp.common.exception.BaseException;
import com.dyy.tsp.common.utils.ByteUtil;
import com.dyy.tsp.core.base.IStatus;
import com.dyy.tsp.core.evgb.enumtype.LatitudeType;
import com.dyy.tsp.core.evgb.enumtype.LongitudeType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.ByteOrder;

/**
 * 位置数据
 */
@SuppressWarnings("all")
public class LocationData implements IStatus {

    /**
     * 定位状态 解析出是否有效定位 经纬度类型
     */
    private Short status;

    /**
     * 是否有效定位
     */
    private Boolean valid;

    /**
     * 经度类型
     */
    private LongitudeType longitudeType;

    /**
     * 纬度类型
     */
    private LatitudeType latitudeType;

    /**
     * 经度
     */
    private Long longitude;

    /**
     * 纬度
     */
    private Long latitude;

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public LongitudeType getLongitudeType() {
        return longitudeType;
    }

    public void setLongitudeType(LongitudeType longitudeType) {
        this.longitudeType = longitudeType;
    }

    public LatitudeType getLatitudeType() {
        return latitudeType;
    }

    public void setLatitudeType(LatitudeType latitudeType) {
        this.latitudeType = latitudeType;
    }

    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    @Override
    public LocationData decode(ByteBuf byteBuf) throws BaseException {
        LocationData locationData = new LocationData();
        locationData.setStatus(byteBuf.readUnsignedByte());
        locationData.setLongitude(byteBuf.readUnsignedInt());
        locationData.setLatitude(byteBuf.readUnsignedInt());
        char[] chars = ByteUtil.to32BinaryString(locationData.getStatus()).toCharArray();
        locationData.setValid(Constants.CHAR_48 == (chars[chars.length-1]));
        locationData.setLatitudeType(LatitudeType.valuesOfChar(chars[chars.length-2]));
        locationData.setLongitudeType(LongitudeType.valuesOfChar(chars[chars.length-3]));
        return locationData;
    }

    @Override
    public ByteBuf encode() throws BaseException {
        ByteBuf buffer = Unpooled.buffer();
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.writeByte(status);
        buffer.writeInt(longitude.intValue());
        buffer.writeInt(latitude.intValue());
        return buffer;
    }
}
