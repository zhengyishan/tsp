package com.dyy.tsp.core.base;

import com.dyy.tsp.common.exception.BaseException;
import io.netty.buffer.ByteBuf;
import java.io.Serializable;

/**
 * 数据单元接口定义
 * created by dyy
 */
@SuppressWarnings("all")
public interface IStatus<T> extends Serializable {

    /**
     * 根据不同协议版本进行解码
     * @param version
     * @param datas
     * @return
     * @throws BaseException
     */
    T decode(ByteBuf datas) throws BaseException;

    /**
     * 根据不同协议版本进行编码
     * @param version
     * @return
     * @throws BaseException
     */
    ByteBuf encode() throws BaseException;
}
