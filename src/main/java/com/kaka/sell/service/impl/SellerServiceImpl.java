package com.kaka.sell.service.impl;

import com.kaka.sell.dataobject.SellerInfo;
import com.kaka.sell.mapper.SellerInfoMapper;
import com.kaka.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoMapper sellerInfoMapper;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return sellerInfoMapper.findByOpenid(openid);
    }
}
