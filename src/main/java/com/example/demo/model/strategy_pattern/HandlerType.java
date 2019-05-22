package com.example.demo.model.strategy_pattern;

/**
 * @ClassName HandlerType 自定义注解，用于判断订单的类型
 * @Description
 * @Author jackson
 * @Date 2019/5/22 11:15
 * @Version 1.0
 **/

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface HandlerType {

    String value();

}
