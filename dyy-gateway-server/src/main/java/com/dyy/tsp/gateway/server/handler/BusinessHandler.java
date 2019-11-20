package com.dyy.tsp.gateway.server.handler;

import com.alibaba.fastjson.JSONObject;
import com.dyy.tsp.common.entity.VehicleCache;
import com.dyy.tsp.common.enumtype.LibraryType;
import com.dyy.tsp.core.base.AbstractBusinessHandler;
import com.dyy.tsp.core.base.IHandler;
import com.dyy.tsp.core.evgb.entity.EvGBProtocol;
import com.dyy.tsp.gateway.server.common.CachePrefixEnum;
import com.dyy.tsp.gateway.server.common.CommonCache;
import com.dyy.tsp.gateway.server.enumtype.EvGBHandlerType;
import io.netty.channel.Channel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * 业务处理核心类
 * created by dyy
 */
@Service
public class BusinessHandler extends AbstractBusinessHandler implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    private RedisHandler redisHandler;

    @Autowired
    private DebugHandler debugHandler;

    @Override
    public void doBusiness(EvGBProtocol protocol, Channel channel) {
        debugHandler.debugger(protocol);
        EvGBHandlerType evGBHandlerType = EvGBHandlerType.valuesOf(protocol.getCommandType().getId());
        if(evGBHandlerType.getHandler()!=null){
            String key = this.getKey(protocol.getVin());
            VehicleCache vehicleCache = this.findVehicleCache(key);
            protocol.setVehicleCache(vehicleCache);
            IHandler handler = (IHandler) applicationContext.getBean(evGBHandlerType.getHandler());
            handler.doBusiness(protocol,channel);
        }
    }

    /**
     * 生成固定规则Key
     * @param vin
     * @return
     */
    private String getKey(String vin) {
        StringBuffer sb = new StringBuffer();
        sb.append(CachePrefixEnum.VEHICLE_CACHE.getPrefix());
        sb.append(vin);
        return sb.toString();
    }

    /**
     * 二级缓存,减少对Redis的压力
     * @param key
     * @return
     */
    private VehicleCache findVehicleCache(String key) {
        VehicleCache vehicleCache = CommonCache.vehicleCacheMap.get(key);
        if(vehicleCache!=null){
            return vehicleCache;
        }
        String cacheData = redisHandler.get(LibraryType.VEHICLE,key);
        if(StringUtils.isBlank(cacheData)){
            return null;
        }else{
            vehicleCache = JSONObject.parseObject(cacheData,VehicleCache.class);
            CommonCache.vehicleCacheMap.put(key,vehicleCache);
            return vehicleCache;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
