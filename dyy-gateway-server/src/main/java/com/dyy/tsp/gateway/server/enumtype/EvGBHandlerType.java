package com.dyy.tsp.gateway.server.enumtype;

import com.dyy.tsp.core.base.IStatus;
import com.dyy.tsp.core.evgb.entity.*;
import com.dyy.tsp.gateway.server.handler.*;

public enum EvGBHandlerType {

    //上行指令
    VEHICLE_LOGIN((short)1, "车辆登入",new VehicleLogin(), VehicleHandler.class),
    VEHICLE_LOGOUT((short)2,"车辆登出",new VehicleLogout(),VehicleHandler.class),
    REALTIME_DATA_REPORTING((short)3,"实时信息上报",new RealTimeData(), RealTimeDataHandler.class),
    REPLACEMENT_DATA_REPORTING((short)4,"补发信息上报",new RealTimeData(),RealTimeDataHandler.class),
    PLATFORM_LOGIN((short)5,"平台登入",new PlatformLogin(), PlatformHandler.class), //国家过检才用
    PLATFORM_LOGOUT((short)6,"平台登出",new PlatformLogout(),PlatformHandler.class), //国家过检才用
    HEARTBEAT((short)7,"心跳",null, HeartBeatHandler.class),
    TERMINAL_CHECK_TIME((short)8,"终端校时",null, CheckTimeHandler.class),

    //下行指令
    QUERY_COMMAND((short)128,"查询命令",null,null),
    SET_COMMAND((short)129,"设置命令",null,null),
    REMOTE_CONTROL((short)130,"车载终端控制命令",null,null),
    ;

    private Short id;
    private String desc;
    private IStatus status;
    private Class handler;

    EvGBHandlerType(Short id, String desc, IStatus status, Class handler) {
        this.id = id;
        this.desc = desc;
        this.status = status;
        this.handler = handler;
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

    public Class getHandler() {
        return handler;
    }

    public void setHandler(Class handler) {
        this.handler = handler;
    }

    public static EvGBHandlerType valuesOf(Short id) {
        for (EvGBHandlerType enums : EvGBHandlerType.values()) {
            if (enums.getId()==id) {
                return enums;
            }
        }
        return null;
    }

}
