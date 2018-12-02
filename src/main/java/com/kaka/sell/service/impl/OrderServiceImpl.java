package com.kaka.sell.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaka.sell.dataobject.OrderDetail;
import com.kaka.sell.dataobject.OrderMaster;
import com.kaka.sell.dataobject.ProductInfo;
import com.kaka.sell.dto.CartDTO;
import com.kaka.sell.dto.OrderDTO;
import com.kaka.sell.enums.OrderStatusEnum;
import com.kaka.sell.enums.PayStatusEnum;
import com.kaka.sell.enums.ResultEnum;
import com.kaka.sell.exception.SellException;
import com.kaka.sell.mapper.OrderDetailMapper;
import com.kaka.sell.mapper.OrderMasterMapper;
import com.kaka.sell.service.*;
import com.kaka.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private OrderMasterMapper orderMasterMapper;
    @Autowired
    private PushMessageService pushMessageService;
    @Autowired
    private WebSocket webSocket;

    @Autowired
    private PayService payService;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        BigDecimal orderAmount  = new BigDecimal(BigInteger.ZERO);
        //1.查询商品（数量 价格）
        String orderId = KeyUtil.getUniqueKey();
        ArrayList<CartDTO> cartDTOS = new ArrayList<>();
        for (OrderDetail detail:orderDTO.getOrderDetailList()){
            ProductInfo productInfo= productService.findOne(detail.getProductId());
            if (productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            //2.计算总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(detail.getProductQuantity())).add(orderAmount) ;
            //3.订单详情入库
            detail.setDetailId(KeyUtil.getUniqueKey());
            detail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo, detail);
            orderDetailMapper.save(detail);

            //扣库存
            CartDTO cartDTO = new CartDTO(detail.getProductId(),detail.getProductQuantity());
            cartDTOS.add(cartDTO);
        }


        //3.写入订单数据库
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterMapper.save(orderMaster);
        //4.扣库存

        productService.decreaseStock(cartDTOS);
        orderDTO.setOrderId(orderId);

        //发送websocket消息
        webSocket.sendMessage(orderDTO.getOrderId());
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterMapper.findOne(orderId);
        if (orderMaster == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        List<OrderDetail> orderDetailList = orderDetailMapper.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    //查询某buyerOpenid订单
    @Override
    public PageInfo<OrderDTO> findList(String buyerOpenid, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<OrderMaster> orderMasterList = orderMasterMapper.findByBuyerOpenId(buyerOpenid);
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (OrderMaster orderMaster:orderMasterList){
            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(orderMaster, orderDTO);
            orderDTO.setOrderDetailList(orderDetailMapper.findByOrderId(orderMaster.getOrderId()));
            orderDTOList.add(orderDTO);
        }

        PageInfo pageInfo = new PageInfo(orderDTOList);
        return pageInfo;
    }

    //查询订单列表
    @Override
    public PageInfo<OrderDTO> findList(Integer currentPage, Integer pageSize) {
        Page page = PageHelper.startPage(currentPage, pageSize);
        List<OrderMaster> orderMasterList = orderMasterMapper.findAll();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (OrderMaster orderMaster:orderMasterList){
            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(orderMaster, orderDTO);
            orderDTO.setOrderDetailList(orderDetailMapper.findByOrderId(orderMaster.getOrderId()));
            orderDTOList.add(orderDTO);
        }

        PageInfo pageInfo = new PageInfo(page);
        pageInfo.setList(orderDTOList);
        return pageInfo;
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[取消订单] 订单状态不正确 orderId={} orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        Integer updateResult = orderMasterMapper.update(orderMaster);
        if (updateResult == 0){
            log.error("[取消订单] 更新失败 orderMaster = {}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }
        //返还库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("[取消订单] 订单i详情为空 orderDto = {}",orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = new ArrayList<>();
        for (OrderDetail orderDetail:orderDTO.getOrderDetailList()){
            CartDTO cartDTO = new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity());
            cartDTOList.add(cartDTO);
        }
        productService.increaseStock(cartDTOList);
        //（如果已支付）退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            payService.refund(orderDTO);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[订单完结] 订单状态不正确，orderId = {},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        Integer updateResult = orderMasterMapper.update(orderMaster);
        if (updateResult == 0){
            log.error("[完结订单] 更新失败 orderMaster = {}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }

        //推送微信模板消息
        pushMessageService.orderStatus(orderDTO);
        return orderDTO;

    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[订单支付完成] 订单状态不正确，orderId = {},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("[订单支付完成] 订单支付状态不正确 orderDTO={}",orderDTO );
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //修改状态
        orderDTO.setPayStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        Integer updateResult = orderMasterMapper.update(orderMaster);
        if (updateResult == 0){
            log.error("[订单支付完成] 更新失败 orderMaster = {}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }
        return orderDTO;
    }
}
