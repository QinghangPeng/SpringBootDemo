package com.example.demo.vo;

public class Circle {

    private Gps center;
    private Double radius;

    public Circle(){

    }

    public Circle(Gps center, Double radius) {
        this.center=center;
        this.radius=radius;
    }

    public Gps getCenter() {
        return center;
    }

    public void setCenter(Gps center) {
        this.center = center;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }
}
