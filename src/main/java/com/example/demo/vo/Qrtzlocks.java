package com.example.demo.vo;

import java.io.Serializable;

/**
 * @ClassName Qrtzlocks
 * @Description
 * @Author jackson
 * @Date 2019/5/14 16:29
 * @Version 1.0
 **/
public class Qrtzlocks implements Serializable {

    private String schedName;
    private String lockName;

    public String getSchedName() {
        return schedName;
    }

    public void setSchedName(String schedName) {
        this.schedName = schedName;
    }

    public String getLockName() {
        return lockName;
    }

    public void setLockName(String lockName) {
        this.lockName = lockName;
    }

    @Override
    public String toString() {
        return "Qrtzlocks{" +
                "schedName='" + schedName + '\'' +
                ", lockName='" + lockName + '\'' +
                '}';
    }
}
