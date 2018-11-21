package com.kaka.sell.service;

import com.github.pagehelper.PageInfo;
import com.kaka.sell.dataobject.OrderDetail;
import com.kaka.sell.dto.OrderDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    OrderService orderService;



    @Test
    public void create() {
//        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
//        OrderDetail detail1 = new OrderDetail();
//
//
//        detail1.setProductId("123");
//
//        detail1.setProductQuantity(11);
//        orderDetails.add(detail1);
//
//
//        OrderDTO orderDTO = new OrderDTO();
//
//        orderDTO.setBuyerName("111");
//        orderDTO.setBuyerPhone("111");
//        orderDTO.setBuyerAddress("111");
//        orderDTO.setBuyerOpenid("111");
//
//        orderDTO.setOrderDetailList(orderDetails);
//        orderService.create(orderDTO);
//        PageInfo<OrderDTO> list = orderService.findList("111", 0, 1);
//        System.out.println(list);

        OrderDTO orderDTO = orderService.findOne("1542714557304106690");
        OrderDTO cancel = orderService.paid(orderDTO);

    }
}