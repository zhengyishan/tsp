package com.dyy.tsp.gateway.dispatcher.kafka;

import com.dyy.tsp.common.asyn.TaskPool;
import com.dyy.tsp.common.kafka.AbstractListener;
import com.dyy.tsp.gateway.dispatcher.handler.DispatcherHandler;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@SuppressWarnings("all")
public class DispatcherListener extends AbstractListener<String,String> {

    private DispatcherHandler dispatcherHandler;

    @PostConstruct
    public void init(){
        dispatcherHandler = context.getBean(DispatcherHandler.class);
    }

    @Override
    protected void listen(String message) {
        TaskPool.getInstance().submitTask(new Runnable() {
            @Override
            public void run() {
                dispatcherHandler.doDistribution(message);
            }
        });
    }

    @PreDestroy
    public void destroy(){
        setRunning(false);
    }

}
