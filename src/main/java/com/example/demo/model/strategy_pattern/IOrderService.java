package com.example.demo.model.strategy_pattern;

import com.example.demo.vo.OrderDTO;
import org.springframework.stereotype.Service;

/**
 * @ClassName IOrderService
 * @Description
 * @Author jackson
 * @Date 2019/5/22 11:09
 * @Version 1.0
 **/
public interface IOrderService {

    /**
     * 根据订单的不同类型作出不同处理
     * @param dto 订单实体
     * @return 为了简单，返回字符串
     */
    String handle(OrderDTO dto);
}
