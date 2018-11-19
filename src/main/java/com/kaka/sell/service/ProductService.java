package com.kaka.sell.service;

import com.kaka.sell.dataobject.ProductInfo;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;

import java.util.List;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
/**
 * @Author yc
 * @Date 2018/11/19 15:35
 */
public interface ProductService {
    ProductInfo findOne(String productId);
    List<ProductInfo> findUpAll();
    //查询所有在架商品列表
    List<ProductInfo> findAll(int currentPage,int pageSize);

    void save(ProductInfo productInfo);

    //库存加减

}
