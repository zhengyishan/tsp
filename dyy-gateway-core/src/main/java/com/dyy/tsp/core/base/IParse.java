package com.dyy.tsp.core.base;


import com.alibaba.fastjson.JSONObject;
import com.dyy.tsp.common.exception.BaseException;
import com.dyy.tsp.core.evgb.enumtype.CommandType;
import io.netty.buffer.ByteBuf;

/**
 * 数据解析顶层接口
 * created by dyy
 */
@SuppressWarnings("all")
public interface IParse {

    JSONObject parseUpJson(CommandType commandType,ByteBuf body) throws BaseException;

    JSONObject parseDownJson(CommandType CommandType,ByteBuf body) throws BaseException;

}
