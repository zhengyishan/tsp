package com.dyy.tsp.core.evgb.enumtype;

import com.dyy.tsp.core.base.IStatus;
import com.dyy.tsp.core.evgb.entity.*;

/**
 * 实时数据上报中的九组数据
 */
@SuppressWarnings("all")
public enum RealTimeDataType {

    VEHICLE((short)1, "整车数据", new VehicleData()),
    DRIVEMOTOR((short)2,"驱动电机数据", new DriveMotorData()),
    FUELCELL((short)3,"燃料电池数据", new FuelCellData()),
    ENGINE((short)4,"发动机数据", new EngineData()),
    LOCATION((short)5,"车辆位置",new LocationData()),
    EXTREME((short)6,"极值数据",new ExtremeData()),
    ALARM((short)7,"报警数据",new AlarmData()),
    VOLTAGE((short)8,"可充电储能装置电压数据",new SubsystemVoltageData()),
    TEMPERATURE((short)9,"可充电储能装置温度数据", new SubsystemTemperatureData()),
    ;

    private Short id;
    private String desc;
    private IStatus status;

    RealTimeDataType(Short id, String desc, IStatus status) {
        this.id = id;
        this.desc = desc;
        this.status = status;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public IStatus getStatus() {
        return status;
    }

    public void setStatus(IStatus status) {
        this.status = status;
    }

    public static RealTimeDataType valuesOf(short id) {
        for (RealTimeDataType enums : RealTimeDataType.values()) {
            if (enums.getId().shortValue()==id) {
                return enums;
            }
        }
        return null;
    }
}
