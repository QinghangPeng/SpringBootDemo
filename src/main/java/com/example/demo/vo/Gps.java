package com.example.demo.vo;

public class Gps {
    private double lat;
    private double lng;

    public Gps(){

    }

    public Gps(double lng, double lat) {
        setLat(lat);
        setLng(lng);
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return lat + "," + lng;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;

        if (!(o instanceof Gps)) {
            return false;
        }

        Gps gps = (Gps) o;
        return gps.getLat() == lat && gps.getLng() == lng;
    }
}
