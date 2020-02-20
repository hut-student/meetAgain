package com.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

public class MyMiniUtils {

    /**
     * 自定义范围随机数
     *
     * @param range  随机数范围
     * @param length 随机数长度
     * @return
     */
    public static String randomNumber(String range, int length) {
        StringBuffer randomNumber = new StringBuffer();
        Random random = new Random();
        for (int n = 0; n < length; n++) {
            randomNumber.append(range.charAt(random.nextInt(range.length())));
        }
        range = randomNumber.toString();
        return range;
    }

    /**
     * 加密
     *
     * @param uid
     * @param time
     * @return
     */
    public static String getEncryptString(String uid, Long time) {
        String str = "*&" + uid + "&*" + getTimeFormat(time) + "**";
        char[] hex = new char[]{'0', '1', '2', '3', '4', '5',
                '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] digest = md.digest();
            int length = digest.length;
            char[] chars = new char[length * 2];
            int num = 0;
            for (byte bt : digest) {
                chars[num++] = hex[bt >>> 4 & 0xf];
                chars[num++] = hex[bt & 0xf];
            }
            return new String(chars);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 时间格式设置
     *
     * @param time
     * @return
     */
    private static String getTimeFormat(Long time) {
        SimpleDateFormat format = new SimpleDateFormat("MM*dd*yyyy&&mm#HH#ss");
        return format.format(time);
}


    /**
     * LocaldateTime到System.currentTimeMillis的转换
     *
     * @param localDateTime
     * @return
     */
    public static Long timeMillisChangeLocalDateTime(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()).getTime();
    }

    /**
     * TimeMillis到LocalDateTime的转换
     *
     * @param time
     * @return
     */
    public static LocalDateTime localDateTimeChangeTimeMillis(Long time) {
        return new Date(time).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * 文件上传，图片上传
     *
     * @param file
     * @param dir
     * @return
     */
    public static String upLoadFile(MultipartFile file, String dir) {
        //String oldFileName = file.getName();  返回参数的名称  这里返回的也就是  file
        //得到文件名
        String oldFileNamea = file.getOriginalFilename();   //得到原来的文件名在客户机的文件系统名称
        //得到最后一个“.”的位置
        int index = oldFileNamea.lastIndexOf('.');
        //得到后缀名
        String exeName = oldFileNamea.substring(index);
        //文件重更名
        String newFileName = System.currentTimeMillis() + MyMiniUtils.randomNumber("0123456789", 4) + exeName;
        //传到路径保存
        File descFile = new File(dir, newFileName);
        try {
            file.transferTo(descFile);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("文件保存失败");
            return null;
        }
        return newFileName;
    }


}
