package com.example.demo.model.strategy_pattern;

import java.io.IOError;
import java.util.Map;

/**
 * @ClassName HandlerContext
 * @Description
 * @Author jackson
 * @Date 2019/5/22 11:25
 * @Version 1.0
 **/
public class HandlerContext {

    private Map<String,Class> handlerMap;

    public HandlerContext(Map<String, Class> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public AbstractHandler getInstance(String type) {
        Class clazz = handlerMap.get(type);
        if (clazz == null) {
            throw new IllegalArgumentException("not found handler for type: " + type);
        }
        return (AbstractHandler) BeanTool.getBean(clazz);
    }
}
