package com.dyy.tsp.core.evgb.enumtype;

import com.dyy.tsp.common.exception.BaseException;

/**
 * 经度类型
 * created by dyy
 */
@SuppressWarnings("all")
public enum LongitudeType {

    EAST("0", "东经"),
    WEST("1", "西经");

    private String binary;
    private String desc;

    LongitudeType(String binary, String desc) {
        this.binary = binary;
        this.desc = desc;
    }

    public String getBinary() {
        return binary;
    }

    public void setBinary(String binary) {
        this.binary = binary;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static LongitudeType valuesOfChar(char aChar) {
        switch (aChar) {
            case (char)48:
                return EAST;
            case (char)49:
                return WEST;
            default:
                throw new BaseException("Unknown LatitudeType Char : " + aChar);
        }
    }

    public static LongitudeType valuesOfBinary(String binary) {
        switch (binary) {
            case "0":
                return EAST;
            case "1":
                return WEST;
            default:
                throw new BaseException("Unknown LatitudeType binary : " + binary);
        }
    }
}
