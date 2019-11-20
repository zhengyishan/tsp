package com.dyy.tsp.core.base;

import com.alibaba.fastjson.JSONObject;
import com.dyy.tsp.common.exception.BaseException;
import com.dyy.tsp.core.evgb.enumtype.CommandType;
import io.netty.buffer.ByteBuf;

/**
 * 实现顶层方法，子类重写对应方法
 * created by dyy
 */
@SuppressWarnings("all")
public class AbstractParseHandler implements IParse{

    @Override
    public JSONObject parseUpJson(CommandType commandType, ByteBuf body) throws BaseException {
        return null;
    }

    @Override
    public JSONObject parseDownJson(CommandType CommandType, ByteBuf body) throws BaseException {
        return null;
    }
}
