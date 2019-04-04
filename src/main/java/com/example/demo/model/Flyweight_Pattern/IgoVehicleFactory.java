package com.example.demo.model.Flyweight_Pattern;

import java.util.Hashtable;

/**
 * @ClassName IgoVehicleFactory
 * @Description
 * @Author jackson
 * @Date 2019/4/4 15:15
 * @Version 1.0
 **/
public class IgoVehicleFactory {

    private static IgoVehicleFactory instance = new IgoVehicleFactory();
    /**
     * 使用HashTable来存储享元对象，充当享元池
     */
    private static Hashtable ht;

    private IgoVehicleFactory() {
        ht = new Hashtable();
        IgoVehicle black,white;
        black = new BlackIgoVehicle();
        ht.put("black",black);
        white = new WhiteIgoVehicle();
        ht.put("white",white);
    }

    /**
     * 返回工厂的唯一实例
     * @return
     */
    public static IgoVehicleFactory getInstance() {
        return instance;
    }

    /**
     * 通过key来获取存储在Hashtable中的享元对象
     * @param color
     * @return
     */
    public IgoVehicle getIgoVehicle(String color) {
        return (IgoVehicle) ht.get(color);
    }

}
