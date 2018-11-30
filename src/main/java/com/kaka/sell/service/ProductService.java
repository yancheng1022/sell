package com.kaka.sell.service;

import com.github.pagehelper.PageInfo;
import com.kaka.sell.dataobject.ProductInfo;
import com.kaka.sell.dto.CartDTO;
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
    PageInfo<ProductInfo> findAllOnSell(int currentPage, int pageSize);

    void save(ProductInfo productInfo);

    PageInfo<ProductInfo> findAll(int currentPage, int pageSize);

    //库存加减
    void increaseStock(List<CartDTO> cartDTOList);
    void decreaseStock(List<CartDTO> cartDTOList);

    //上架
    void onSale(String productId);
    //下架
    void offSale(String productId);

    void update(ProductInfo productInfo);

}
