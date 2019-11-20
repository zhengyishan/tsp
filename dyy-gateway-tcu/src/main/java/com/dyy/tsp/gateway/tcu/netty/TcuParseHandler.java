package com.dyy.tsp.gateway.tcu.netty;

import com.alibaba.fastjson.JSONObject;
import com.dyy.tsp.common.exception.BaseException;
import com.dyy.tsp.core.base.AbstractParseHandler;
import com.dyy.tsp.core.base.IStatus;
import com.dyy.tsp.core.evgb.enumtype.CommandType;
import com.dyy.tsp.gateway.tcu.enumtype.TcuHandlerType;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Service;

/**
 * 数据解析处理器
 * created by dyy
 */
@SuppressWarnings("all")
@Service
public class TcuParseHandler extends AbstractParseHandler {

    /**
     * 解析Tbox的上行JSON报文方法
     * @param commandType
     * @param byteBuf
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject parseDownJson(CommandType commandType, ByteBuf byteBuf) throws BaseException {
        IStatus status = TcuHandlerType.valuesOf(commandType.getId()).getStatus();
        return status == null ? null : (JSONObject) JSONObject.toJSON(status.decode(byteBuf));
    }
}