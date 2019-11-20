package com.dyy.tsp.gateway.server.handler;

import com.dyy.tsp.core.base.AbstractBusinessHandler;
import com.dyy.tsp.core.evgb.entity.EvGBProtocol;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 实时信息上报与补发信息上报处理
 * 按照国家标准不需要给终端响应
 * created by dyy
 */
@Service
@SuppressWarnings("all")
public class RealTimeDataHandler extends AbstractBusinessHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RealTimeDataHandler.class);

    @Autowired
    private ForwardHandler forwardHandler;

    @Override
    public void doBusiness(EvGBProtocol protrocol, Channel channel) {
        switch (protrocol.getCommandType()){
            case REALTIME_DATA_REPORTING: {
                this.doRealtimeData(protrocol,channel);
                break;
            }
            case REPLACEMENT_DATA_REPORTING:{
                this.doRealtimeData(protrocol,channel);
                break;
            }
            default:
                break;
        }
    }

    /**
     * 实时信息上报与补发信息上报逻辑一致。共用一个方法
     * 按照国家标准不需要给终端响应
     * @param protrocol
     * @param channel
     */
    private void doRealtimeData(EvGBProtocol protrocol, Channel channel) {
        forwardHandler.sendToDispatcher(protrocol);
    }
}
