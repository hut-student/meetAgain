package com.utils;

import com.pojo.*;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@Service
public class MyMiniUtils {

    @Value("${web.site}")
    private  String webSite;

    @Value("${upload.headPortrait.dir}")
    private  String headPhoto;


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

    /**
     * 将MultipatrFile转为Base64编码
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static String multipartFileToBase64(MultipartFile file) throws Exception {
        return ImageToBase64(multipartFileToFile(file));
    }

    /**
     * MultipartFile 转 File
     *
     * @param file
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile file) throws Exception {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * file转base64
     *
     * @param file
     * @return
     */
    public static String ImageToBase64(File file) {
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(file);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串
        return encoder.encode(Objects.requireNonNull(data));
    }

    /**
     * searchComment转为newsComment
     *
     * @param user     用户对象
     * @param comment  评论
     * @param replySum 回复数
     * @return
     */
    public  NewsComment searchCommentToNewsComment(User user, Comment comment, Integer replySum, Integer commentSum) {
        NewsComment newsComment =new NewsComment(comment.getcId(), comment.getuId(), comment.getsId().toString(), comment.getcComment(), comment.getcFloor(), MyMiniUtils.timeMillisChangeLocalDateTime(comment.getcTime()), user.getuName(), "https://" + webSite + headPhoto + user.getuHeadPortrait(), replySum);
        newsComment.setCommentSum(commentSum);
        return newsComment;
    }

    public  NewsComment searchCommentToNewsComment(User user, Comment comment, Integer replySum) {
        return searchCommentToNewsComment(user, comment, replySum, null);
    }


    /**
     * newsComment转为searchComment
     * @param newsComment
     * @return
     */
    public static Comment newsCommentToSearchComment(NewsComment newsComment){
        return new Comment(newsComment.getuId(), Integer.valueOf(newsComment.getXwId()), newsComment.getXwcComment(), LocalDateTime.now());
    }

    /**
     * searchReply转为NewReply
     * @param reply
     * @param fromUser
     * @param toUser
     * @return
     */
    public  NewsReply searchReplyToNewsReply(Reply reply, User fromUser, User toUser){
        NewsReply newsReply = new NewsReply();
        newsReply.setXwrId(reply.getId());
        newsReply.setXwcId(reply.getcId());
        newsReply.setXwrFloor(reply.getToFloor());
        newsReply.setrFloor(reply.getrFloor());
        newsReply.setFromUid(reply.getFromUid());
        newsReply.setToUid(reply.getToUid());
        newsReply.setrComment(reply.getrComment());
        newsReply.setTime(MyMiniUtils.timeMillisChangeLocalDateTime(reply.getrTime()));
        newsReply.setFromUname(fromUser.getuName());
        newsReply.setuHeadPortrait("https://" + webSite + headPhoto + fromUser.getuHeadPortrait());
        newsReply.setToUname(toUser.getuName());
        return newsReply;
    }

    /**
     * 判断两个经纬度之间的距离
     * @param lng1 经度
     * @param lat1 纬度
     * @param lng2 经度
     * @param lat2 纬度
     * @return
     */
    public static double getWGS84Distance(double lng1, double lat1, double lng2, double lat2){
        GlobalCoordinates source = new GlobalCoordinates(lat1, lng1);
        GlobalCoordinates target = new GlobalCoordinates(lat2, lng2);

        GeodeticCurve geodeticCurve = new GeodeticCalculator().calculateGeodeticCurve(Ellipsoid.WGS84, source, target);
        return geodeticCurve.getEllipsoidalDistance();
    }

}
