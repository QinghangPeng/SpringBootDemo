package com.example.demo.model.Flyweight_Pattern;

/**
 * @ClassName Location
 * @Description
 * @Author jackson
 * @Date 2019/4/4 15:50
 * @Version 1.0
 **/
public class Location {

    private Double latitude;
    private Double longitude;

    public Location(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
