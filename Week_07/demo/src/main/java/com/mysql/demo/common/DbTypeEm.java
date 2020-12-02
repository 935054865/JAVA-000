package com.mysql.demo.common;

import java.util.Arrays;
import java.util.Optional;

public enum DbTypeEm {


    db0(0, "db0(默认master)", -1),
    db1(1, "db1", 0),
    ;

    /**
     * 用于筛选从库
     *
     * @param slaveNum 从库顺序编号 0开始
     * @return
     */
    public static Optional<DbTypeEm> getDbTypeBySlaveNum(int slaveNum) {
        return Arrays.stream(DbTypeEm.values()).filter(b -> b.getSlaveNum() == slaveNum).findFirst();
    }

    DbTypeEm(int code, String des, int slaveNum) {
        this.code = code;
        this.des = des;
        this.slaveNum = slaveNum;
    }

    private int code;
    private String des;
    private int slaveNum;

    public int getCode() {
        return code;
    }

    public String getDes() {
        return des;
    }

    public int getSlaveNum() {
        return slaveNum;
    }
}
