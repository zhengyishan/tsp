package com.dyy.tsp.gateway.tcu.api;

import com.dyy.tsp.common.entity.Response;
import com.dyy.tsp.gateway.tcu.handler.TcuHandler;
import com.dyy.tsp.gateway.tcu.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * TCU模拟服务
 * created by dyy
 */
@SuppressWarnings("all")
@Api
@RequestMapping(value = "tcu")
@RestController
public class TcuApi {

    @Autowired
    private TcuHandler tcuHandler;

    @ApiOperation(value = "Redis指令下发")
    @RequestMapping(value = "commandDown",method = RequestMethod.POST)
    public Response commandDown(@RequestBody CommandDownRequestVo commandDownRequestVo){
        tcuHandler.commandDown(commandDownRequestVo);
        return Response.success();
    }

    @ApiOperation(value = "车辆登入")
    @RequestMapping(value = "vehicleLogin",method = RequestMethod.POST)
    public Response vehicleLogin(@RequestBody VehicleLoginVo vehicleLoginVo){
        tcuHandler.vehicleLogin(vehicleLoginVo);
        return Response.success();
    }

    @ApiOperation(value = "车辆登出")
    @RequestMapping(value = "vehicleLogout",method = RequestMethod.POST)
    public Response vehicleLogout(@RequestBody VehicleLogoutVo vehicleLogoutVo){
        tcuHandler.vehicleLogout(vehicleLogoutVo);
        return Response.success();
    }

    @ApiOperation(value = "平台登入")
    @RequestMapping(value = "platformLogin",method = RequestMethod.POST)
    public Response platformLogin(@RequestBody PlatformLoginVo platformLoginVo){
        tcuHandler.platformLogin(platformLoginVo);
        return Response.success();
    }

    @ApiOperation(value = "平台登出")
    @RequestMapping(value = "platformLogout",method = RequestMethod.POST)
    public Response platformLogout(@RequestBody PlatformLogoutVo platformLogoutVo){
        tcuHandler.platformLogout(platformLogoutVo);
        return Response.success();
    }

    @ApiOperation(value = "实时信息上报/补发信息上报")
    @RequestMapping(value = "realtimeData",method = RequestMethod.POST)
    public Response realtimeData(@RequestBody RealTimeDataVo realTimeDataVo){
        tcuHandler.realtimeData(realTimeDataVo);
        return Response.success();
    }

    @ApiOperation(value = "终端校时")
    @RequestMapping(value = "checkTime",method = RequestMethod.GET)
    public Response checkTime(@ApiParam("车架号")@RequestParam(value = "vin") String vin){
        tcuHandler.checkTime(vin);
        return Response.success();
    }
}
