package com.example.demo.model.strategy_pattern.processor;

import com.example.demo.model.strategy_pattern.AbstractHandler;
import com.example.demo.model.strategy_pattern.HandlerType;
import com.example.demo.vo.OrderDTO;
import org.springframework.stereotype.Component;

/**
 * @ClassName GroupHandler
 * @Description
 * @Author jackson
 * @Date 2019/5/22 11:21
 * @Version 1.0
 **/
@Component
@HandlerType("2")
public class GroupHandler extends AbstractHandler {
    @Override
    public String handle(OrderDTO dto) {
        return "这是团购订单";
    }
}
