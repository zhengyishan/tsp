package com.dyy.tsp.core.evgb.enumtype;

/**
 * 数据单元加密方式
 * created by dyy
 */
@SuppressWarnings("all")
public enum EncryptionType {

    NONE((short)1, "不加密"),
    RSA((short)2, "RSA加密"),
    AES((short)3, "AES加密"),
    INVAILD((short)254, "无效"),
    ERROR((short)255,"异常"),
    ;

    private Short id;
    private String desc;

    EncryptionType(Short id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static EncryptionType valuesOf(short id) {
        for (EncryptionType enums : EncryptionType.values()) {
            if (enums.getId().shortValue()==id) {
                return enums;
            }
        }
        return ERROR;
    }
}
