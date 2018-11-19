package com.kaka.sell.mapper;

import com.kaka.sell.dataobject.ProductInfo;
import com.kaka.sell.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoMapperTest {

//    @Autowired
//    ProductInfoMapper productInfoMapper;
    @Autowired
    ProductService productService;
    @Test
    public void select(){
//        ProductInfo productInfo = new ProductInfo();
//        productInfo.setProductId("123456");
//        productInfo.setProductName("奶茶");
//        productInfo.setProductPrice(new BigDecimal(10.2));
//        productInfo.setProductStock(100);
//        productInfo.setProductDescription("好好喝哦");
//        productInfo.setProductIcon("e://img");
//        productInfo.setProductStatus(0);
//        productInfo.setCategoryType(3);
//
//        productInfoMapper.save(productInfo);

//        List<ProductInfo> byProductStatus = productInfoMapper.findByProductStatus(0);
//        for (ProductInfo pro : byProductStatus) {
//            System.out.println(pro);
//        }

        ProductInfo all = productService.findOne("123");
        System.out.println(all);
//        List<ProductInfo> all = productService.findAll(2, 2);
//        for (ProductInfo p:all) {
//            System.out.println(p);
//        }

    }
}