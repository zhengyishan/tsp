package com.dyy.tsp.common.enumtype;

/**
 * 实时补发类型
 */
@SuppressWarnings("all")
public enum InstructionType {

    REALTIME("实时"),
    REPLACEMENT("补发");
    private String decs;

    InstructionType(String decs) {
        this.decs = decs;
    }

    public String getDecs() {
        return decs;
    }

    public void setDecs(String decs) {
        this.decs = decs;
    }

}
