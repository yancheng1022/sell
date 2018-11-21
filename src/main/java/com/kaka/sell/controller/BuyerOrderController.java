package com.kaka.sell.controller;


import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaka.sell.VO.ResultVO;
import com.kaka.sell.converter.OrderForm2OrderDTOConverter;
import com.kaka.sell.dataobject.OrderDetail;
import com.kaka.sell.dto.OrderDTO;
import com.kaka.sell.enums.ResultEnum;
import com.kaka.sell.exception.SellException;
import com.kaka.sell.form.OrderForm;
import com.kaka.sell.service.OrderService;
import com.kaka.sell.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
    @Autowired
    private OrderService orderService;

    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            log.error("[创建订单] 参数不正确，orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                   bindingResult.getFieldError().getDefaultMessage() );
        }
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("[创建订单] 购物车不能为空" );
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO createResult = orderService.create(orderDTO);
        Map<String,String> map = new HashMap<>();
        map.put("orderId", createResult.getOrderId());
        return ResultVOUtil.success(map);

    }
    //订单列表

    @RequestMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid")String openid,
                                         @RequestParam(value = "page",defaultValue = "0")Integer currentPage,
                                         @RequestParam(value = "size",defaultValue = "10")Integer pageSize){
        if (StringUtils.isEmpty(openid)){
            log.error("[查询订单列表] openid为空" );
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageInfo<OrderDTO> orderDTOPage = orderService.findList(openid, currentPage, pageSize);
        return ResultVOUtil.success(orderDTOPage.getList());
    }
    //订单详情


    @RequestMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid")String openid,
                                     @RequestParam("orderId")String orderid){
        //TODO
        OrderDTO orderDTO = orderService.findOne(orderid);
        return ResultVOUtil.success(orderDTO);
    }

    //取消订单
    @RequestMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid")String openid,
                           @RequestParam("orderId")String orderid){
        //TODO
        OrderDTO orderDTO = orderService.findOne(orderid);
        orderService.cancel(orderDTO);
        return ResultVOUtil.success();
    }

}
