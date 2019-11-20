package com.dyy.tsp.core.evgb.entity;

import com.dyy.tsp.common.constants.Constants;
import com.dyy.tsp.common.exception.BaseException;
import com.dyy.tsp.common.utils.ByteUtil;
import com.dyy.tsp.core.base.IStatus;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * 报警数据
 */
@SuppressWarnings("all")
public class AlarmData implements IStatus {

    /**
     * 报警等级
     */
    private Short level;

    /**
     * 报警通用标志
     */
    private Long alarmInfo;

    /**
     * 温度差异报警
     */
    private Boolean temperatureDifferential;

    /**
     * 电池高温报警
     */
    private Boolean batteryHighTemperature;

    /**
     * 车载储能装置类型过压报警
     */
    private Boolean deviceTypeOverVoltage;

    /**
     * 车载储能装置类型欠压报警
     */
    private Boolean deviceTypeUnderVoltage;

    /**
     * SOC过低报警
     */
    private Boolean socLow;

    /**
     * 单体电池过压报警
     */
    private Boolean monomerBatteryOverVoltage;

    /**
     * 单体电池欠压报警
     */
    private Boolean monomerBatteryUnderVoltage;

    /**
     * SOC过高报警
     */
    private Boolean socHigh;

    /**
     * SOC跳变报警
     */
    private Boolean socJump;

    /**
     * 车载储能装置类型不匹配报警
     */
    private Boolean deviceTypeDontMatch;

    /**
     * 单体电池一致性差报警
     */
    private Boolean batteryConsistencyPoor;

    /**
     * 绝缘报警
     */
    private Boolean insulation;

    /**
     * DC温度报警
     */
    private Boolean dcTemperature;

    /**
     * 制动系统报警
     */
    private Boolean brakingSystem;

    /**
     * DC状态报警
     */
    private Boolean dcStatus;

    /**
     * 驱动电机控制器温度报警
     */
    private Boolean driveMotorControllerTemperature;

    /**
     * 高压互锁报警
     */
    private Boolean highPressureInterlock;

    /**
     * 驱动电机温度报警
     */
    private Boolean driveMotorTemperature;

    /**
     * 车载储能装置过充报警
     */
    private Boolean deviceTypeOverFilling;

    /**
     * 可储能装置故障数N1
     */
    private Short deviceFailuresCount;

    /**
     * 可储能装置故障信息列表
     */
    private List<Long> deviceFailuresCodes;

    /**
     * 驱动电机故障数N2
     */
    private Short driveMotorFailuresCount;

    /**
     * 驱动电机故障信息列表
     */
    private List<Long> driveMotorFailuresCodes;

    /**
     * 发动机故障数N3
     */
    private Short engineFailuresCount;

    /**
     * 发动机故障信息列表
     */
    private List<Long> engineFailuresCodes;

    /**
     * 其他故障数N4
     */
    private Short otherFailuresCount;

    /**
     * 其他故障信息列表
     */
    private List<Long> otherFailuresCodes;

    @Override
    public AlarmData decode(ByteBuf byteBuf) throws BaseException {
        AlarmData alarmData = new AlarmData();
        alarmData.setLevel(byteBuf.readUnsignedByte());
        Long l = byteBuf.readUnsignedInt();
        alarmData.setAlarmInfo(l);
        char[] chars = ByteUtil.to32BinaryString(l.intValue()).toCharArray();
        alarmData.setTemperatureDifferential(Constants.CHAR_49 == (chars[chars.length-1]));
        alarmData.setBatteryHighTemperature(Constants.CHAR_49 == (chars[chars.length-2]));
        alarmData.setDeviceTypeOverVoltage(Constants.CHAR_49 == (chars[chars.length-3]));
        alarmData.setDeviceTypeUnderVoltage(Constants.CHAR_49 == (chars[chars.length-4]));
        alarmData.setSocLow(Constants.CHAR_49 == (chars[chars.length-5]));
        alarmData.setMonomerBatteryOverVoltage(Constants.CHAR_49 == (chars[chars.length-6]));
        alarmData.setMonomerBatteryUnderVoltage(Constants.CHAR_49 == (chars[chars.length-7]));
        alarmData.setSocHigh(Constants.CHAR_49 == (chars[chars.length-8]));
        alarmData.setSocJump(Constants.CHAR_49 == (chars[chars.length-9]));
        alarmData.setDeviceTypeDontMatch(Constants.CHAR_49 == (chars[chars.length-10]));
        alarmData.setBatteryConsistencyPoor(Constants.CHAR_49 == (chars[chars.length-11]));
        alarmData.setInsulation(Constants.CHAR_49 == (chars[chars.length-12]));
        alarmData.setDcTemperature(Constants.CHAR_49 == (chars[chars.length-13]));
        alarmData.setBrakingSystem(Constants.CHAR_49 == (chars[chars.length-14]));
        alarmData.setDcStatus(Constants.CHAR_49 == (chars[chars.length-15]));
        alarmData.setDriveMotorControllerTemperature(Constants.CHAR_49 == (chars[chars.length-16]));
        alarmData.setHighPressureInterlock(Constants.CHAR_49 == (chars[chars.length-17]));
        alarmData.setDriveMotorTemperature(Constants.CHAR_49 == (chars[chars.length-18]));
        alarmData.setDeviceTypeOverFilling(Constants.CHAR_49 == (chars[chars.length-19]));
        short deviceFailuresTotal = byteBuf.readUnsignedByte();
        alarmData.setDeviceFailuresCount(deviceFailuresTotal);
        List<Long> deviceFailuresList =new ArrayList<Long>(deviceFailuresTotal);
        for (int i = 0; i <deviceFailuresTotal; i++) {
            deviceFailuresList.add(byteBuf.readUnsignedInt());
        }
        alarmData.setDeviceFailuresCodes(deviceFailuresList);
        short driveMotorFailuresTotal = byteBuf.readUnsignedByte();
        alarmData.setDriveMotorFailuresCount(driveMotorFailuresTotal);
        List<Long> driveMotorFailuresList =new ArrayList<Long>(driveMotorFailuresTotal);
        for (int i = 0; i <driveMotorFailuresTotal; i++) {
            driveMotorFailuresList.add(byteBuf.readUnsignedInt());
        }
        alarmData.setDriveMotorFailuresCodes(driveMotorFailuresList);
        short engineFailuresTotal = byteBuf.readUnsignedByte();
        alarmData.setEngineFailuresCount(engineFailuresTotal);
        List<Long> engineFailuresList =new ArrayList<Long>(engineFailuresTotal);
        for (int i = 0; i <engineFailuresTotal; i++) {
            engineFailuresList.add(byteBuf.readUnsignedInt());
        }
        alarmData.setEngineFailuresCodes(engineFailuresList);
        short otherFailuresTotal = byteBuf.readUnsignedByte();
        alarmData.setOtherFailuresCount(otherFailuresTotal);
        List<Long> otherFailuresTotalList =new ArrayList<Long>(otherFailuresTotal);
        for (int i = 0; i <otherFailuresTotal; i++) {
            otherFailuresTotalList.add(byteBuf.readUnsignedInt());
        }
        alarmData.setOtherFailuresCodes(otherFailuresTotalList);
        return alarmData;
    }

    @Override
    public ByteBuf encode() throws BaseException {
        ByteBuf buffer = Unpooled.buffer();
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.writeByte(level);
        buffer.writeInt(alarmInfo.intValue());
        buffer.writeByte(deviceFailuresCount);
        if(deviceFailuresCount>0 && deviceFailuresCodes.size()>0){
            for (int i = 0; i < deviceFailuresCount; i++) {
                buffer.writeInt(deviceFailuresCodes.get(i).intValue());
            }
        }
        buffer.writeByte(driveMotorFailuresCount);
        if(driveMotorFailuresCount>0 && driveMotorFailuresCodes.size()>0){
            for (int i = 0; i < driveMotorFailuresCount; i++) {
                buffer.writeInt(driveMotorFailuresCodes.get(i).intValue());
            }
        }
        buffer.writeByte(engineFailuresCount);
        if(engineFailuresCount>0 && engineFailuresCodes.size()>0){
            for (int i = 0; i < engineFailuresCount; i++) {
                buffer.writeInt(engineFailuresCodes.get(i).intValue());
            }
        }
        buffer.writeByte(otherFailuresCount);
        if(otherFailuresCount>0 && otherFailuresCodes.size()>0){
            for (int i = 0; i < otherFailuresCount; i++) {
                buffer.writeInt(otherFailuresCodes.get(i).intValue());
            }
        }
        return buffer;
    }

    public Short getLevel() {
        return level;
    }

    public void setLevel(Short level) {
        this.level = level;
    }

    public Long getAlarmInfo() {
        return alarmInfo;
    }

    public void setAlarmInfo(Long alarmInfo) {
        this.alarmInfo = alarmInfo;
    }

    public Boolean getTemperatureDifferential() {
        return temperatureDifferential;
    }

    public void setTemperatureDifferential(Boolean temperatureDifferential) {
        this.temperatureDifferential = temperatureDifferential;
    }

    public Boolean getBatteryHighTemperature() {
        return batteryHighTemperature;
    }

    public void setBatteryHighTemperature(Boolean batteryHighTemperature) {
        this.batteryHighTemperature = batteryHighTemperature;
    }

    public Boolean getDeviceTypeOverVoltage() {
        return deviceTypeOverVoltage;
    }

    public void setDeviceTypeOverVoltage(Boolean deviceTypeOverVoltage) {
        this.deviceTypeOverVoltage = deviceTypeOverVoltage;
    }

    public Boolean getDeviceTypeUnderVoltage() {
        return deviceTypeUnderVoltage;
    }

    public void setDeviceTypeUnderVoltage(Boolean deviceTypeUnderVoltage) {
        this.deviceTypeUnderVoltage = deviceTypeUnderVoltage;
    }

    public Boolean getSocLow() {
        return socLow;
    }

    public void setSocLow(Boolean socLow) {
        this.socLow = socLow;
    }

    public Boolean getMonomerBatteryOverVoltage() {
        return monomerBatteryOverVoltage;
    }

    public void setMonomerBatteryOverVoltage(Boolean monomerBatteryOverVoltage) {
        this.monomerBatteryOverVoltage = monomerBatteryOverVoltage;
    }

    public Boolean getMonomerBatteryUnderVoltage() {
        return monomerBatteryUnderVoltage;
    }

    public void setMonomerBatteryUnderVoltage(Boolean monomerBatteryUnderVoltage) {
        this.monomerBatteryUnderVoltage = monomerBatteryUnderVoltage;
    }

    public Boolean getSocHigh() {
        return socHigh;
    }

    public void setSocHigh(Boolean socHigh) {
        this.socHigh = socHigh;
    }

    public Boolean getSocJump() {
        return socJump;
    }

    public void setSocJump(Boolean socJump) {
        this.socJump = socJump;
    }

    public Boolean getDeviceTypeDontMatch() {
        return deviceTypeDontMatch;
    }

    public void setDeviceTypeDontMatch(Boolean deviceTypeDontMatch) {
        this.deviceTypeDontMatch = deviceTypeDontMatch;
    }

    public Boolean getBatteryConsistencyPoor() {
        return batteryConsistencyPoor;
    }

    public void setBatteryConsistencyPoor(Boolean batteryConsistencyPoor) {
        this.batteryConsistencyPoor = batteryConsistencyPoor;
    }

    public Boolean getInsulation() {
        return insulation;
    }

    public void setInsulation(Boolean insulation) {
        this.insulation = insulation;
    }

    public Boolean getDcTemperature() {
        return dcTemperature;
    }

    public void setDcTemperature(Boolean dcTemperature) {
        this.dcTemperature = dcTemperature;
    }

    public Boolean getBrakingSystem() {
        return brakingSystem;
    }

    public void setBrakingSystem(Boolean brakingSystem) {
        this.brakingSystem = brakingSystem;
    }

    public Boolean getDcStatus() {
        return dcStatus;
    }

    public void setDcStatus(Boolean dcStatus) {
        this.dcStatus = dcStatus;
    }

    public Boolean getDriveMotorControllerTemperature() {
        return driveMotorControllerTemperature;
    }

    public void setDriveMotorControllerTemperature(Boolean driveMotorControllerTemperature) {
        this.driveMotorControllerTemperature = driveMotorControllerTemperature;
    }

    public Boolean getHighPressureInterlock() {
        return highPressureInterlock;
    }

    public void setHighPressureInterlock(Boolean highPressureInterlock) {
        this.highPressureInterlock = highPressureInterlock;
    }

    public Boolean getDriveMotorTemperature() {
        return driveMotorTemperature;
    }

    public void setDriveMotorTemperature(Boolean driveMotorTemperature) {
        this.driveMotorTemperature = driveMotorTemperature;
    }

    public Boolean getDeviceTypeOverFilling() {
        return deviceTypeOverFilling;
    }

    public void setDeviceTypeOverFilling(Boolean deviceTypeOverFilling) {
        this.deviceTypeOverFilling = deviceTypeOverFilling;
    }

    public Short getDeviceFailuresCount() {
        return deviceFailuresCount;
    }

    public void setDeviceFailuresCount(Short deviceFailuresCount) {
        this.deviceFailuresCount = deviceFailuresCount;
    }

    public List<Long> getDeviceFailuresCodes() {
        return deviceFailuresCodes;
    }

    public void setDeviceFailuresCodes(List<Long> deviceFailuresCodes) {
        this.deviceFailuresCodes = deviceFailuresCodes;
    }

    public Short getDriveMotorFailuresCount() {
        return driveMotorFailuresCount;
    }

    public void setDriveMotorFailuresCount(Short driveMotorFailuresCount) {
        this.driveMotorFailuresCount = driveMotorFailuresCount;
    }

    public List<Long> getDriveMotorFailuresCodes() {
        return driveMotorFailuresCodes;
    }

    public void setDriveMotorFailuresCodes(List<Long> driveMotorFailuresCodes) {
        this.driveMotorFailuresCodes = driveMotorFailuresCodes;
    }

    public Short getEngineFailuresCount() {
        return engineFailuresCount;
    }

    public void setEngineFailuresCount(Short engineFailuresCount) {
        this.engineFailuresCount = engineFailuresCount;
    }

    public List<Long> getEngineFailuresCodes() {
        return engineFailuresCodes;
    }

    public void setEngineFailuresCodes(List<Long> engineFailuresCodes) {
        this.engineFailuresCodes = engineFailuresCodes;
    }

    public Short getOtherFailuresCount() {
        return otherFailuresCount;
    }

    public void setOtherFailuresCount(Short otherFailuresCount) {
        this.otherFailuresCount = otherFailuresCount;
    }

    public List<Long> getOtherFailuresCodes() {
        return otherFailuresCodes;
    }

    public void setOtherFailuresCodes(List<Long> otherFailuresCodes) {
        this.otherFailuresCodes = otherFailuresCodes;
    }
}
