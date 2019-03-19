package com.example.demo.vo;

import java.util.List;

public class Polygon {

    List<Gps> path;
    public Polygon() {

    }

    public Polygon(List<Gps> path) {
        this.path=path;
    }

    public List<Gps> getPath() {
        return path;
    }

    public void setPath(List<Gps> path) {
        this.path = path;
    }
}
