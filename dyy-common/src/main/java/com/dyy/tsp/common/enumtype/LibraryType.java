package com.dyy.tsp.common.enumtype;

/**
 * Redis各库存储类型
 * created by dyy
 */
@SuppressWarnings("all")
public enum LibraryType {

    VEHICLE(0,"车辆缓存数据"),
    LOCATTION(1,"最新位置"),
    TRANSIENT(2,"瞬时态"),
    COMMAND(3,"远控控制指令下发"),
    ;

    private Integer database;
    private String desc;

    LibraryType(Integer flag, String desc) {
        this.database = database;
        this.desc = desc;
    }

    public Integer getDatabase() {
        return database;
    }

    public void setDatabase(Integer database) {
        this.database = database;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
