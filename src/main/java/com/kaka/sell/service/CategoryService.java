package com.kaka.sell.service;

import com.kaka.sell.dataobject.ProductCategory;

import java.util.List;

/**
 * @Author yc
 * @Date 2018/11/19 11:32
 */
public interface CategoryService {
    ProductCategory findOne(Integer categoryId);
    List<ProductCategory> findAll();
    List<ProductCategory> findByCategoryType(List<Integer> categoryTypeList);
    ProductCategory save(ProductCategory productCategory);

}
