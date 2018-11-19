package com.kaka.sell.service.impl;

import com.kaka.sell.dataobject.ProductCategory;
import com.kaka.sell.mapper.ProductCategoryMapper;
import com.kaka.sell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private ProductCategoryMapper productCategoryMapper;
    @Override
    public ProductCategory findOne(Integer categoryId) {
        return productCategoryMapper.findOne(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return productCategoryMapper.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryType(List<Integer> categoryTypeList) {
        return productCategoryMapper.findByCategoryType(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return productCategoryMapper.save(productCategory);
    }
}
