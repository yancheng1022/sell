package com.kaka.sell.utils;

import java.util.Random;

public class KeyUtil {
    //生成唯一的主键 时间+随机数
    public static synchronized String getUniqueKey(){
        Random random = new Random();
        //生成6位数随机数
        Integer number = random.nextInt(900000)+100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }
}
