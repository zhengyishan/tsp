package com.dyy.tsp.core.evgb.entity;

import com.dyy.tsp.common.exception.BaseException;
import com.dyy.tsp.core.base.IStatus;
import com.dyy.tsp.core.evgb.enumtype.RealTimeDataType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * 实时数据上报
 */
@SuppressWarnings("all")
public class RealTimeData implements IStatus {

    private static final Logger LOGGER = LoggerFactory.getLogger(RealTimeData.class);

    private static final BeanTime producer = new BeanTime();

    /**
     * 数据采集时间
     */
    private BeanTime beanTime;

   /**
     * 整车数据
     */
    private VehicleData vehicleData;

    /**
     * 驱动电机个数
     */
    private Short driveMotorCount;

    /**
     * 驱动电机数据列表
     */
    private List<DriveMotorData> driveMotorDatas;

    /**
     * 燃料电池数据
     */
    private FuelCellData fuelCellData;

    /**
     * 发动机数据
     */
    private EngineData engineData;

    /**
     * 位置数据
     */
    private LocationData locationData;

    /**
     * 极值数据
     */
    private ExtremeData extremeData;

    /**
     * 报警数据
     */
    private AlarmData alarmData;

    /**
     * 可充电储能装置电压数据个数
     */
    private Short subsystemVoltageCount;

    /**
     * 可充电储能装置电压数据列表
     */
    private List<SubsystemVoltageData> subsystemVoltageDatas;

    /**
     * 可充电储能装置温度数据个数
     */
    private Short subsystemTemperatureCount;

    /**
     * 可充电储能装置温度数据列表
     */
    private List<SubsystemTemperatureData> subsystemTemperatureDatas;

    @Override
    public RealTimeData decode(ByteBuf byteBuf) throws BaseException {
        RealTimeData realTimeData = new RealTimeData();
        BeanTime beanTime = producer.decode(byteBuf);
        realTimeData.setBeanTime(beanTime);
        while (byteBuf.isReadable()) {
            decodeByType(byteBuf, realTimeData);
        }
        return realTimeData;
    }

    @Override
    public ByteBuf encode() throws BaseException {
        ByteBuf buffer = Unpooled.buffer();
        buffer.order(ByteOrder.BIG_ENDIAN);
        buffer.writeBytes(beanTime.encode());
        if (vehicleData != null) {
            buffer.writeByte(RealTimeDataType.VEHICLE.getId());
            buffer.writeBytes(vehicleData.encode());
        }
        if (driveMotorCount > 0 && driveMotorDatas != null) {
            buffer.writeByte(RealTimeDataType.DRIVEMOTOR.getId());
            buffer.writeByte(driveMotorCount);
            for (int i = 0; i < driveMotorCount; i++) {
                buffer.writeBytes(driveMotorDatas.get(i).encode());
            }
        }
        if (fuelCellData != null) {
            buffer.writeByte(RealTimeDataType.FUELCELL.getId());
            buffer.writeBytes(fuelCellData.encode());
        }
        if (engineData != null) {
            buffer.writeByte(RealTimeDataType.ENGINE.getId());
            buffer.writeBytes(engineData.encode());
        }
        if (locationData != null) {
            buffer.writeByte(RealTimeDataType.LOCATION.getId());
            buffer.writeBytes(locationData.encode());
        }
        if (extremeData != null) {
            buffer.writeByte(RealTimeDataType.EXTREME.getId());
            buffer.writeBytes(extremeData.encode());
        }
        if (alarmData != null) {
            buffer.writeByte(RealTimeDataType.ALARM.getId());
            buffer.writeBytes(alarmData.encode());
        }
        if (subsystemVoltageCount > 0 && subsystemVoltageDatas != null) {
            buffer.writeByte(RealTimeDataType.VOLTAGE.getId());
            buffer.writeByte(subsystemVoltageCount);
            for (int i = 0; i < subsystemVoltageCount; i++) {
                buffer.writeBytes(subsystemVoltageDatas.get(i).encode());
            }
        }
        if (subsystemTemperatureCount > 0 && subsystemTemperatureDatas != null) {
            buffer.writeByte(RealTimeDataType.TEMPERATURE.getId());
            buffer.writeByte(subsystemTemperatureCount);
            for (int i = 0; i < subsystemTemperatureCount; i++) {
                buffer.writeBytes(subsystemTemperatureDatas.get(i).encode());
            }
        }
        return buffer;
    }

    /**
     * 解析不同的车辆类型数据
     *
     * @param byteBuf
     * @param type
     */
    private void decodeByType(ByteBuf byteBuf, RealTimeData realtimeData) {
        RealTimeDataType type = RealTimeDataType.valuesOf(byteBuf.readUnsignedByte());
        switch (type) {
            case VEHICLE: {
                IStatus<VehicleData> status = type.getStatus();
                realtimeData.setVehicleData(status.decode(byteBuf.readSlice(20)));
                break;
            }
            case DRIVEMOTOR: {
                IStatus<DriveMotorData> status = type.getStatus();
                realtimeData.setDriveMotorCount(byteBuf.readUnsignedByte());
                List<DriveMotorData> motorDatas = new ArrayList<>();
                if (realtimeData.getDriveMotorCount() > 0 && realtimeData.getDriveMotorCount() <= 253) {
                    for (int i = 0; i < realtimeData.getDriveMotorCount(); i++) {
                        motorDatas.add(status.decode(byteBuf));
                    }
                } else {
                    LOGGER.error("decodeByType {} count:{}", type.getDesc(), realtimeData.getDriveMotorCount());
                }
                realtimeData.setDriveMotorDatas(motorDatas);
                break;
            }
            case FUELCELL: {
                IStatus<FuelCellData> status = type.getStatus();
                realtimeData.setFuelCellData(status.decode(byteBuf));
                break;
            }
            case ENGINE: {
                IStatus<EngineData> status = type.getStatus();
                realtimeData.setEngineData(status.decode(byteBuf));
                break;
            }
            case LOCATION: {
                IStatus<LocationData> status = type.getStatus();
                realtimeData.setLocationData(status.decode(byteBuf));
                break;
            }
            case EXTREME: {
                IStatus<ExtremeData> status = type.getStatus();
                realtimeData.setExtremeData(status.decode(byteBuf));
                break;
            }
            case ALARM: {
                IStatus<AlarmData> status = type.getStatus();
                realtimeData.setAlarmData(status.decode(byteBuf));
                break;
            }
            case VOLTAGE: {
                IStatus<SubsystemVoltageData> status = type.getStatus();
                realtimeData.setSubsystemVoltageCount(byteBuf.readUnsignedByte());
                List<SubsystemVoltageData> voltageData = new ArrayList<>();
                if (realtimeData.getSubsystemVoltageCount() > 0 && realtimeData.getSubsystemVoltageCount() <= 250) {
                    for (int i = 0; i < realtimeData.getSubsystemVoltageCount(); i++) {
                        byteBuf.markReaderIndex();
                        byteBuf.skipBytes(9);
                        short j = byteBuf.readUnsignedByte();
                        byteBuf.resetReaderIndex();
                        voltageData.add(status.decode(byteBuf.readSlice(10 + j * 2)));
                    }
                } else {
                    LOGGER.error("decodeByType {} count:{}", type.getDesc(), realtimeData.getDriveMotorCount());
                }
                realtimeData.setSubsystemVoltageDatas(voltageData);
                break;
            }
            case TEMPERATURE: {
                IStatus<SubsystemTemperatureData> status = type.getStatus();
                realtimeData.setSubsystemTemperatureCount(byteBuf.readUnsignedByte());
                List<SubsystemTemperatureData> temperatureData = new ArrayList<>();
                if (realtimeData.getSubsystemTemperatureCount() > 0 && realtimeData.getSubsystemTemperatureCount() <= 250) {
                    for (int i = 0; i < realtimeData.getSubsystemTemperatureCount(); i++) {
                        byteBuf.markReaderIndex();
                        byteBuf.skipBytes(1);
                        int j = byteBuf.readUnsignedShort();
                        byteBuf.resetReaderIndex();
                        temperatureData.add(status.decode(byteBuf.readSlice(3 + j * 1)));
                    }
                } else {
                    LOGGER.error("decodeByType {} count:{}", type.getDesc(), realtimeData.getDriveMotorCount());
                }
                realtimeData.setSubsystemTemperatureDatas(temperatureData);
                break;
            }
            default:
                LOGGER.warn("decode RealtimeData error");
        }
    }

    public VehicleData getVehicleData() {
        return vehicleData;
    }

    public void setVehicleData(VehicleData vehicleData) {
        this.vehicleData = vehicleData;
    }

    public Short getDriveMotorCount() {
        return driveMotorCount;
    }

    public void setDriveMotorCount(Short driveMotorCount) {
        this.driveMotorCount = driveMotorCount;
    }

    public List<DriveMotorData> getDriveMotorDatas() {
        return driveMotorDatas;
    }

    public void setDriveMotorDatas(List<DriveMotorData> driveMotorDatas) {
        this.driveMotorDatas = driveMotorDatas;
    }

    public FuelCellData getFuelCellData() {
        return fuelCellData;
    }

    public void setFuelCellData(FuelCellData fuelCellData) {
        this.fuelCellData = fuelCellData;
    }

    public EngineData getEngineData() {
        return engineData;
    }

    public void setEngineData(EngineData engineData) {
        this.engineData = engineData;
    }

    public LocationData getLocationData() {
        return locationData;
    }

    public void setLocationData(LocationData locationData) {
        this.locationData = locationData;
    }

    public ExtremeData getExtremeData() {
        return extremeData;
    }

    public void setExtremeData(ExtremeData extremeData) {
        this.extremeData = extremeData;
    }

    public AlarmData getAlarmData() {
        return alarmData;
    }

    public void setAlarmData(AlarmData alarmData) {
        this.alarmData = alarmData;
    }

    public Short getSubsystemVoltageCount() {
        return subsystemVoltageCount;
    }

    public void setSubsystemVoltageCount(Short subsystemVoltageCount) {
        this.subsystemVoltageCount = subsystemVoltageCount;
    }

    public List<SubsystemVoltageData> getSubsystemVoltageDatas() {
        return subsystemVoltageDatas;
    }

    public void setSubsystemVoltageDatas(List<SubsystemVoltageData> subsystemVoltageDatas) {
        this.subsystemVoltageDatas = subsystemVoltageDatas;
    }

    public Short getSubsystemTemperatureCount() {
        return subsystemTemperatureCount;
    }

    public void setSubsystemTemperatureCount(Short subsystemTemperatureCount) {
        this.subsystemTemperatureCount = subsystemTemperatureCount;
    }

    public List<SubsystemTemperatureData> getSubsystemTemperatureDatas() {
        return subsystemTemperatureDatas;
    }

    public void setSubsystemTemperatureDatas(List<SubsystemTemperatureData> subsystemTemperatureDatas) {
        this.subsystemTemperatureDatas = subsystemTemperatureDatas;
    }

    public BeanTime getBeanTime() {
        return beanTime;
    }

    public void setBeanTime(BeanTime beanTime) {
        this.beanTime = beanTime;
    }
}
