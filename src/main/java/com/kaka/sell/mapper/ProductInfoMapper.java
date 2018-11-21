package com.kaka.sell.mapper;

import com.kaka.sell.dataobject.ProductInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductInfoMapper {
    List<ProductInfo> findByProductStatus(Integer productStatus);
    void save(ProductInfo productInfo);
    ProductInfo findOne(String productId);
    List<ProductInfo> findAll(int currentPage,int pageSize);

    void update(ProductInfo productInfo);
}
