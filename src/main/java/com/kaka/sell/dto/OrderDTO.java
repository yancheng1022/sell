package com.kaka.sell.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.kaka.sell.dataobject.OrderDetail;
import com.kaka.sell.enums.OrderStatusEnum;
import com.kaka.sell.enums.PayStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
    private String orderId;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String buyerOpenid;
    private BigDecimal orderAmount;
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();
    private Integer payStatus = PayStatusEnum.WAIT.getCode();
    private Date createTime;
    private Date updateTime;
    private List<OrderDetail> orderDetailList;
}
