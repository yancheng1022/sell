package com.kaka.sell.config;


import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
@Component
public class WechatPayConfig {
    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    @Bean
    public BestPayServiceImpl bestPayService(){
        WxPayH5Config wxPayH5Config = new WxPayH5Config();
        //公众平台id
        wxPayH5Config.setAppId(wechatAccountConfig.getMpAppId());
        //公众平台密钥
        wxPayH5Config.setAppSecret(wechatAccountConfig.getMpAppSecret());
        //商户号
        wxPayH5Config.setMchId(wechatAccountConfig.getMchId());
        //商户密钥
        wxPayH5Config.setMchKey(wechatAccountConfig.getMchKey());
        //商户证书路径
        wxPayH5Config.setKeyPath(wechatAccountConfig.getKeyPath());
        //微信支付异步通知地址
        wxPayH5Config.setNotifyUrl(wechatAccountConfig.getNotifyUrl());

        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayH5Config(wxPayH5Config);
        return bestPayService;
    }
}
