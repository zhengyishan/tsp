package com.dyy.tsp.gateway.tcu.handler;

import com.dyy.tsp.core.base.AbstractBusinessHandler;
import com.dyy.tsp.core.base.IHandler;
import com.dyy.tsp.core.evgb.entity.EvGBProtocol;
import com.dyy.tsp.gateway.tcu.enumtype.TcuHandlerType;
import io.netty.channel.Channel;
import org.springframework.beans.BeansException;
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

    @Override
    public void doBusiness(EvGBProtocol protrocol, Channel channel) {
        TcuHandlerType tcuHandlerType = TcuHandlerType.valuesOf(protrocol.getCommandType().getId());
        if(tcuHandlerType.getHandler()!=null){
            IHandler handler = (IHandler) applicationContext.getBean(tcuHandlerType.getHandler());
            handler.doBusiness(protrocol,channel);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
