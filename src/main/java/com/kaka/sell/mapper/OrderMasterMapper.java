package com.kaka.sell.mapper;

import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import com.kaka.sell.dataobject.OrderMaster;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMasterMapper {
    void save(OrderMaster orderMaster);
    List<OrderMaster> findByBuyerOpenId(@Param("buyerOpenId") String buyerOpenId);
    OrderMaster findOne(String orderId);
    Integer update(OrderMaster orderMaster);

    List<OrderMaster> findAll();
}
