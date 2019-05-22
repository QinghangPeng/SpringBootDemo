package com.example.demo.model.Flyweight_Pattern;

/**
 * @ClassName IgoVehicle
 * @Description 汽车颜色类：抽象享元类
 * @Author jackson
 * @Date 2019/4/4 14:47
 * @Version 1.0
 **/
public abstract class IgoVehicle {

    public abstract String getColor();

    public void display() {
        System.out.println("汽车颜色：" + this.getColor());
    }

    public void location(Location location) {
        System.out.println("汽车颜色：" + this.getColor()+",汽车定位："+ location.getLongitude()+","+location.getLatitude());
    }
}
