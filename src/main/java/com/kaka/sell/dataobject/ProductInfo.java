package com.kaka.sell.dataobject;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author yc
 * @Date 2018/11/19 12:17
 */

@Data
public class ProductInfo {

    private String productId;
    private String productName;
    private BigDecimal productPrice;
    private Integer productStock;
    private String productDescription;
    private String productIcon;
    //商品状态 0-正常 1-下架
    private Integer productStatus;
    private Integer categoryType;




}
