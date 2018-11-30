package com.kaka.sell.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kaka.sell.enums.OrderStatusEnum;
import com.kaka.sell.enums.ProductStatusEnum;
import com.kaka.sell.utils.EnumUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

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
    /** 创建时间. */
    private Date createTime;

    /** 更新时间. */
    private Date updateTime;

    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum(){
        return EnumUtil.getByCode(productStatus,ProductStatusEnum.class );
    }


}
