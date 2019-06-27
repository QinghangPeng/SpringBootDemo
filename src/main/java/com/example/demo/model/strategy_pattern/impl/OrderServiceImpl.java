package com.example.demo.model.strategy_pattern.impl;

import com.example.demo.model.strategy_pattern.AbstractHandler;
import com.example.demo.model.strategy_pattern.HandlerContext;
import com.example.demo.model.strategy_pattern.IOrderService;
import com.example.demo.vo.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @ClassName OrderServiceImpl
 * @Description
 * @Author jackson
 * @Date 2019/5/22 11:12
 * @Version 1.0
 **/
@Service
@Qualifier("orderServiceImpl")
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private HandlerContext handlerContext;

    @Override
    public String handle(OrderDTO dto) {
        AbstractHandler handler = handlerContext.getInstance(dto.getType());
        return handler.handle(dto);
    }
}
