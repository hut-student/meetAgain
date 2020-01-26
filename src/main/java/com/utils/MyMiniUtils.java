package com.utils;

import java.util.Random;

public class MyMiniUtils {

    /**
     * 自定义范围随机数
     * @param Range 随机数范围
     * @param length 随机数长度
     * @return
     */
    public static String randomNumber(String Range, int length){
        StringBuffer randomNumber = new StringBuffer();
        Random random = new Random();
        for (int n = 0; n < length; n++){
            randomNumber.append(Range.charAt(random.nextInt(Range.length())));
        }
        Range = randomNumber.toString();
        return Range;
    }

}
