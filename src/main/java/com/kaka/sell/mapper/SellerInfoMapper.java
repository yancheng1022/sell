package com.kaka.sell.mapper;

import com.kaka.sell.dataobject.SellerInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SellerInfoMapper {
    SellerInfo findByOpenid(String openid);
    void save(SellerInfo sellerInfo);
}
