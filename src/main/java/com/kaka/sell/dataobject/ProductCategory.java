package com.kaka.sell.dataobject;

import lombok.Data;

import java.util.Date;


@Data
public class ProductCategory {
    /** 类目id. */
    private Integer categoryId;

    /** 类目名字. */
    private String categoryName;

    /** 类目编号. */
    private Integer categoryType;

    /** 创建时间. */
    private Date createTime;

    /** 更新时间. */
    private Date updateTime;


}
