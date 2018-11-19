package com.kaka.sell.service.impl;

import com.github.pagehelper.PageHelper;
import com.kaka.sell.dataobject.ProductInfo;
import com.kaka.sell.enums.ProductStatusEnum;
import com.kaka.sell.mapper.ProductInfoMapper;
import com.kaka.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductInfoMapper productInfoMapper;
    @Override
    public ProductInfo findOne(String productId) {
        return productInfoMapper.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoMapper.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfo> findAll(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<ProductInfo> allProducts = productInfoMapper.findByProductStatus(0);
//        int counts = allProducts.size();
//        PageBean<ProductInfo> pageData = new PageBean<>(currentPage, pageSize, counts);
//        pageData.setItems(allProducts);
        return allProducts;
    }


    @Override
    public void save(ProductInfo productInfo) {
         productInfoMapper.save(productInfo);
    }
}
