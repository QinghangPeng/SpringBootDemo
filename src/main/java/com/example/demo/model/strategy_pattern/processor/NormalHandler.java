package com.example.demo.model.strategy_pattern.processor;

import com.example.demo.model.strategy_pattern.AbstractHandler;
import com.example.demo.model.strategy_pattern.HandlerType;
import com.example.demo.vo.OrderDTO;
import org.springframework.stereotype.Component;

/**
 * @ClassName NormalHandler
 * @Description
 * @Author jackson
 * @Date 2019/5/22 11:20
 * @Version 1.0
 **/
@Component
@HandlerType("1")
public class NormalHandler extends AbstractHandler {
    @Override
    public String handle(OrderDTO dto) {
        return "这是普通订单";
    }
}
