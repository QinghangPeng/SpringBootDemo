package com.example.demo.vo;

import java.util.List;

public class Rectangle extends Polygon {

    //西南脚点
    private Gps southWest;

    //东北脚点
    private Gps northEast;

    public Rectangle(List<Gps> path) {
        super(path);

        northEast = super.path.get(1);
        southWest = super.path.get(3);
    }

    public Gps getSouthWest() {
        return southWest;
    }

    public void setSouthWest(Gps southWest) {
        this.southWest = southWest;
    }

    public Gps getNorthEast() {
        return northEast;
    }

    public void setNorthEast(Gps northEast) {
        this.northEast = northEast;
    }
}
