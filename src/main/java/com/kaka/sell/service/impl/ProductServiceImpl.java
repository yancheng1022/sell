package com.kaka.sell.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaka.sell.dataobject.ProductInfo;
import com.kaka.sell.dto.CartDTO;
import com.kaka.sell.enums.ProductStatusEnum;
import com.kaka.sell.enums.ResultEnum;
import com.kaka.sell.exception.SellException;
import com.kaka.sell.mapper.ProductInfoMapper;
import com.kaka.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public PageInfo<ProductInfo> findAllOnSell(int currentPage, int pageSize) {
        Page page = PageHelper.startPage(currentPage, pageSize);
        List<ProductInfo> allProducts = productInfoMapper.findByProductStatus(0);
        PageInfo<ProductInfo> pageInfo = new PageInfo(page);
        pageInfo.setList(allProducts);
        return pageInfo;
    }


    @Override
    public void save(ProductInfo productInfo) {
//        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        productInfoMapper.save(productInfo);
    }

    @Override
    public PageInfo<ProductInfo> findAll(int currentPage, int pageSize) {
        Page page = PageHelper.startPage(currentPage, pageSize);
        List<ProductInfo> allProducts = productInfoMapper.findAll(currentPage, pageSize);
        PageInfo<ProductInfo> pageInfo = new PageInfo(page);
        pageInfo.setList(allProducts);
        return pageInfo;
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO:cartDTOList){
            ProductInfo productInfo = productInfoMapper.findOne(cartDTO.getProductId());
            if (productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            productInfoMapper.update(productInfo);
        }

    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO:cartDTOList){
            ProductInfo productInfo = productInfoMapper.findOne(cartDTO.getProductId());
            if (productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();

            if (result < 0){
                throw  new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }

            productInfo.setProductStock(result);
            productInfoMapper.update(productInfo);
        }
    }

    @Override
    public void onSale(String productId) {
        ProductInfo productInfo = productInfoMapper.findOne(productId);
        if (productInfo == null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.UP){
            throw new SellException(ResultEnum.PRODUCT_STATE_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        productInfoMapper.update(productInfo);

    }

    @Override
    public void offSale(String productId) {
        ProductInfo productInfo = productInfoMapper.findOne(productId);
        if (productInfo == null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN){
            throw new SellException(ResultEnum.PRODUCT_STATE_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfoMapper.update(productInfo);
    }

    @Override
    public void update(ProductInfo productInfo) {
        productInfoMapper.update(productInfo);
    }
}
