package com.example.demo.enums;

public enum FaultMoudle {

    AMT("code1",1,"描述1"),
    DEG("code2",2,"描述2"),
    SUE("code3",3,"描述3");

    String code;
    Integer value;
    String describe;

    FaultMoudle(String code, Integer value, String describe) {
        this.code = code;
        this.value = value;
        this.describe = describe;
    }

    public static Integer getValueByCode(String code) {
        for (FaultMoudle faultMoudle : FaultMoudle.values()) {
            if (faultMoudle.getCode().equals(code)) {
                return faultMoudle.value;
            }
        }
        return  null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
