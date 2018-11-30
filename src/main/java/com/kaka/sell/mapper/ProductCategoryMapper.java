package com.kaka.sell.mapper;


import com.kaka.sell.dataobject.ProductCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author yc
 * @Date 2018/11/18 19:49
 */
@Mapper
public interface ProductCategoryMapper {

    List<ProductCategory> findByCategoryType(List<Integer> categoryTypeList);
    ProductCategory findOne(Integer categoryId);
    List<ProductCategory> findAll();
    void save(ProductCategory productCategory);

    void update(ProductCategory productCategory);
}
