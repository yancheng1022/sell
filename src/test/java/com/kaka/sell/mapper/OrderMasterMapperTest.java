package com.kaka.sell.mapper;

import com.kaka.sell.dataobject.OrderDetail;
import com.kaka.sell.dataobject.OrderMaster;
import com.kaka.sell.dto.OrderDTO;
import com.kaka.sell.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterMapperTest {

    @Autowired
    private OrderMasterMapper orderMasterMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private OrderService orderService;

    @Test
    public void test1(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("555");
        orderMaster.setBuyerName("555");
        orderMaster.setOrderAmount(new BigDecimal(111));
        orderMaster.setBuyerAddress("555");
        orderMaster.setBuyerOpenid("555");
        orderMaster.setBuyerPhone("555");
        orderMaster.setOrderStatus(0);
        orderMaster.setPayStatus(0);
        orderMasterMapper.save(orderMaster);
    }

    @Test
    public void test2(){
        List<OrderMaster> list = orderMasterMapper.findByBuyerOpenId("1111");
        for(OrderMaster orderMaster:list){
            System.out.println(orderMaster);
        }
    }


    @Test
    public void test3(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("111");
        orderDetail.setOrderId("111");
        orderDetail.setProductIcon("111");
        orderDetail.setProductId("111");
        orderDetail.setProductName("111");
        orderDetail.setProductPrice(new BigDecimal(111));
        orderDetail.setProductQuantity(111);
        orderDetailMapper.save(orderDetail);
    }

    @Test
    public void test4(){
        OrderDTO one = orderService.findOne("1542714557304106690");
        System.out.println(one);
    }
}