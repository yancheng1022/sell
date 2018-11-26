package com.kaka.sell.service;

import com.github.pagehelper.PageInfo;
import com.kaka.sell.dataobject.OrderMaster;
import com.kaka.sell.dto.OrderDTO;

public interface OrderService {
    //创建订单
    OrderDTO create(OrderDTO orderDTO);

    //查询单个订单
    OrderDTO findOne(String orderId);

    //查询订单列表
    PageInfo<OrderDTO> findList(String buyerOpenid, Integer currentPage, Integer pageSize);

    //查询所有订单
    PageInfo<OrderDTO> findList(Integer currentPage, Integer pageSize);

    //取消订单
    OrderDTO cancel(OrderDTO orderDTO);

    //完结订单
    OrderDTO finish(OrderDTO orderDTO);

    //支付订单
    OrderDTO paid(OrderDTO orderDTO);
}
