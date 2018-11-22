package com.kaka.sell.controller;

import com.kaka.sell.exception.AesException;
import com.kaka.sell.utils.WXPublicUtils;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import sun.util.calendar.LocalGregorianCalendar;

import javax.servlet.http.HttpServletRequest;

@RestController
//@RequestMapping("/weixin")
@Slf4j
public class WeiXinController {

    @RequestMapping("/wxpublic/verify_wx_token")
    public String verifyWXToken(HttpServletRequest request) throws AesException {
        String msgSignature = request.getParameter("signature");
        String msgTimestamp = request.getParameter("timestamp");
        String msgNonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        if (WXPublicUtils.verifyUrl(msgSignature, msgTimestamp, msgNonce)) {
            return echostr;
        }
        return null;
    }



    @GetMapping("/weixin/auth")
    public void auth(@RequestParam("code")String code){
        log.info("auth...");
        log.info("code={}",code);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx1be3e368ddbde589&secret=035374cad92db65a6a5efcd153c1a92d&code="+code+"&grant_type=authorization_code";
        RestTemplate template = new RestTemplate();
        String response = template.getForObject(url, String.class);
        log.info("response={}",response);
    }
}
