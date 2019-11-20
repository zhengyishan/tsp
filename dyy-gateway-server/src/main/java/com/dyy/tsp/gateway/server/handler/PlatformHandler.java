package com.dyy.tsp.gateway.server.handler;

import com.dyy.tsp.core.base.AbstractBusinessHandler;
import com.dyy.tsp.core.evgb.entity.EvGBProtocol;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 平台登入，平台登出处理器。需要给终端响应
 * 终端应该遵循国家标准,必须支持该两个指令,完成国家抽检直连考试
 *
 * 平台转发过检不实现该两个指令逻辑！！！
 * created by dyy
 */
@Service
@SuppressWarnings("all")
public class PlatformHandler extends AbstractBusinessHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlatformHandler.class);

    @Override
    public void doBusiness(EvGBProtocol protrocol, Channel channel) {
        switch (protrocol.getCommandType()){
            case PLATFORM_LOGIN: {
                this.doPlatformLogin(protrocol,channel);
                break;
            }
            case PLATFORM_LOGOUT:{
                this.doPlatformLogout(protrocol,channel);
                break;
            }
            default:
                break;
        }
    }

    /**
     * 平台登入
     * @param protrocol
     * @param channel
     */
    private void doPlatformLogout(EvGBProtocol protrocol, Channel channel) {

    }

    /**
     * 平台登出
     * @param protrocol
     * @param channel
     */
    private void doPlatformLogin(EvGBProtocol protrocol, Channel channel) {

    }


}
