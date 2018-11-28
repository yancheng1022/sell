package com.kaka.sell.controller;

import com.github.pagehelper.PageInfo;
import com.kaka.sell.dto.OrderDTO;
import com.kaka.sell.enums.OrderStatusEnum;
import com.kaka.sell.enums.ResultEnum;
import com.kaka.sell.exception.SellException;
import com.kaka.sell.service.OrderService;
import com.lly835.bestpay.rest.type.Get;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.print.DocFlavor;
import java.util.Map;

@Controller
@RequestMapping("seller/order")
@Slf4j
public class SellerOrderController {

    @Autowired
    private OrderService orderService;
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                             @RequestParam(value = "size",defaultValue = "10")Integer size,
                             Map<String,Object> map){

        PageInfo<OrderDTO> orderList = orderService.findList(page, size);
        map.put("orderDTOPage",orderList );
        return new ModelAndView("order/list",map);

    }

    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId")String orderId, Map<String,Object>map){
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.cancel(orderDTO);
        }catch (SellException e){
            log.error("【卖家端取消订单】 发生异常{}",e);
            map.put("msg",e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error");
        }
        map.put("msg",ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success");
    }

    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId")String orderId,Map<String,Object>map){
        OrderDTO orderDTO = new OrderDTO();
        try {
            orderDTO = orderService.findOne(orderId);
        }catch (SellException e){
            log.error("【卖家端取消订单】 发生异常{}",e);
            map.put("msg",e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        map.put("orderDTO", orderDTO);
        return new ModelAndView("/order/detail",map);
    }

    @GetMapping("/finish")
    public ModelAndView finished(@RequestParam("orderId")String orderId,Map<String,Object>map){
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.finish(orderDTO);
        }catch (SellException e){
            log.error("【卖家端完结订单】 发生异常{}",e);
            map.put("msg",e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error");
        }
        map.put("msg",ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success");
    }
}
