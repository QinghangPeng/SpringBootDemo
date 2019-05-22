package com.example.demo.model.Flyweight_Pattern;

/**
 * @ClassName 享元模式
 * @Description
 * @Author jackson
 * @Date 2019/4/4 15:35
 * @Version 1.0
 **/
public class Client {

    public static void main(String[] args) {
        IgoVehicle black1,black2,black3,white1,white2;
        IgoVehicleFactory factory;

        factory = IgoVehicleFactory.getInstance();

        //通过享元工厂获取三辆黑色汽车
        black1 = factory.getIgoVehicle("black");
        black2 = factory.getIgoVehicle("black");
        black3 = factory.getIgoVehicle("black");
        System.out.println("判断两辆车是否相同："+ (black1 == black2));

        //通过享元工厂获取两辆白色汽车
        white1 = factory.getIgoVehicle("white");
        white2 = factory.getIgoVehicle("white");
        System.out.println("判断两辆车是否相同："+ (white1 == white2));

        //显示车辆
        black1.display();
        black2.display();
        black3.display();
        white1.display();
        white2.display();

        //显示不同位置的车辆
        black1.location(new Location(123.456789,24.345678));
        black2.location(new Location(234.567891,45.123456));
        black3.location(new Location(456.78912,56.123456));
        white1.location(new Location(789.123456,67.123456));
        white1.location(new Location(901.123456,12.234567));
    }
}
