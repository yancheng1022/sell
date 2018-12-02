package com.kaka.sell.service;

import com.kaka.sell.dataobject.SellerInfo;

public interface SellerService {
    SellerInfo findSellerInfoByOpenid(String openid);

}
