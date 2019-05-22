package com.example.demo.model.strategy_pattern;

import com.example.demo.vo.OrderDTO;

/**
 * @ClassName AbstractHandler
 * @Description
 * @Author jackson
 * @Date 2019/5/22 11:14
 * @Version 1.0
 **/
public abstract class AbstractHandler {

    abstract public String handle(OrderDTO dto);
}
