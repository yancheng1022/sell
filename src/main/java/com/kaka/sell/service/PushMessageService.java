package com.kaka.sell.service;

import com.kaka.sell.dto.OrderDTO;

public interface PushMessageService {
    void orderStatus(OrderDTO orderDTO);
}
