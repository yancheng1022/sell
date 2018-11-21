package com.kaka.sell.mapper;


import com.kaka.sell.dataobject.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapper {

    void save(OrderDetail orderDetail);
    List<OrderDetail> findByOrderId(String orderId);
}
