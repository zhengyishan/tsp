package com.dyy.tsp.core.evgb.entity;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import io.netty.buffer.ByteBuf;

/**
 * 数据单元
 * created by dyy
 */
@SuppressWarnings("all")
public class DataBody {

    private JSONObject json;

    @JSONField(serialize = false)
    private ByteBuf byteBuf;

    public JSONObject getJson() {
        return json;
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }

    public ByteBuf getByteBuf() {
        return byteBuf;
    }

    public void setByteBuf(ByteBuf byteBuf) {
        this.byteBuf = byteBuf;
    }

    public DataBody() {
    }

    public DataBody(ByteBuf byteBuf) {
        this.byteBuf = byteBuf;
    }
}
