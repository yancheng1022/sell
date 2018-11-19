package com.kaka.sell.mapper;

import com.kaka.sell.dataobject.ProductCategory;
import com.kaka.sell.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class productCategoryTest {


    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Autowired
    private CategoryService categoryService;
    @Test
    public void insert() {
//        ProductCategory productCategory = new ProductCategory();
//        productCategory.setCategoryName("中兴");
//        productCategory.setCategoryType(4);
//        productCategoryMapper.save(productCategory);

//        List<Integer> list = new ArrayList<>();
//        list.add(1);
//        list.add(2);
//        List<ProductCategory> byCategoryType = productCategoryMapper.findByCategoryType(list);
//        for (ProductCategory p:byCategoryType) {
//            System.out.println(p);
//        }

//        List<ProductCategory> all = categoryService.findAll();
//        for (ProductCategory pro :all) {
//            System.out.println(pro);
//        }

        ProductCategory one = categoryService.findOne(1);
        System.out.println(one);
    }
}