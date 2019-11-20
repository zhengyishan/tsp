package com.dyy.tsp.core.base;

import com.alibaba.fastjson.JSONObject;
import com.dyy.tsp.core.evgb.entity.DataBody;
import com.dyy.tsp.core.evgb.entity.EvGBProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * 网关核心解码器
 * 动态支持Redis合包与内存合包
 * created by dyy
 */
@SuppressWarnings("all")
public class EvGBDecode extends AbstractDecode<EvGBProtocol> {

    private EvGBProtocol producer = new EvGBProtocol();

    public EvGBDecode(IRedis redisHandler, IParse parseHandler, Boolean flag, Boolean memoryConsolidation) {
        super(redisHandler, parseHandler, flag, memoryConsolidation);
    }

    @Override
    protected EvGBProtocol decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        EvGBProtocol protocol = producer.decode(byteBuf);
        DataBody body = protocol.getBody();
        if(body!=null && protocol.getLength()>0){
            JSONObject json = null;
            if(this.getFlag()){
                json = parseHandler.parseUpJson(protocol.getCommandType(), body.getByteBuf());
            }else{
                json = parseHandler.parseDownJson(protocol.getCommandType(),body.getByteBuf());
            }
            body.setJson(json);
        }
        protocol.setBody(body);
        return protocol;
    }
}
