package com.example.demo.vo.domain;

public class SpeedAndRpmStatisticsDTO implements BaseVo {
    int speedAndRpmId;
    int speedId;
    int count;
    String id;
    String tboxId;
    String vin;
    Long tboxTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTboxId() {
        return tboxId;
    }

    public void setTboxId(String tboxId) {
        this.tboxId = tboxId;
    }

    @Override
    public String getVin() {
        return vin;
    }

    @Override
    public Long getSortTime() {
        return tboxTime;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Long getTboxTime() {
        return tboxTime;
    }

    public void setTboxTime(Long tboxTime) {
        this.tboxTime = tboxTime;
    }

    public int getSpeedAndRpmId() {
        return speedAndRpmId;
    }

    public void setSpeedAndRpmId(int speedAndRpmId) {
        this.speedAndRpmId = speedAndRpmId;
    }

    public int getSpeedId() {
        return speedId;
    }

    public void setSpeedId(int speedId) {
        this.speedId = speedId;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "SpeedAndRpmStatisticsDTO{" +
                "speedAndRpmId=" + speedAndRpmId +
                ", speedId=" + speedId +
                ", count=" + count +
                ", id='" + id + '\'' +
                ", tboxId='" + tboxId + '\'' +
                ", vin='" + vin + '\'' +
                ", tboxTime=" + tboxTime +
                '}';
    }

    public void setCount(int count) {
        this.count = count;
    }
}
