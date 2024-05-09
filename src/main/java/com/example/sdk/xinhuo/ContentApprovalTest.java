package com.example.sdk.xinhuo;

import com.google.common.collect.Maps;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * | Author: kfbi2
 * | Date: 2024/04/12 11:12
 * | Desc: 内容审核服务，接入
 * | Version: 1.0.0
 * | Update:
 */
public class ContentApprovalTest {


    public static void main(String[] args) throws Exception {
        String accessKeySecret = "Lh723Hh6VUL08g7137cJ3XB9Pq21ywg9";
        Map<String, String> queryMap = Maps.newHashMap();
        queryMap.put("utc", generateUTC());
        queryMap.put("appId", "K56H9K");
        queryMap.put("accessKeyId", "0kbj17ur8G");
        String uuid = UUID.randomUUID().toString().replace("-", "");
        System.out.println("uuid: " + uuid);
        queryMap.put("uuid", uuid);
        String signature = signature(accessKeySecret, queryMap);
        System.out.println("signature: " + signature);
    }

    /**
     * 生成UTC时间
     *
     * @return
     */
    public static String generateUTC() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        Date date = new Date();
//        DateTime offset = DateUtil.offset(date, DateField.HOUR, -8);
        String formattedDate = dateFormat.format(date);
        System.out.println("generateUTC: " + formattedDate);
        return formattedDate;
    }

    /**
     * 生成验签签名
     *
     * @param accessKeySecret
     * @param queryParam
     * @return
     * @throws Exception
     */
    public static String signature(String accessKeySecret, Map<String, String> queryParam) throws Exception {
        //排序
        TreeMap<String, String> treeMap = new TreeMap<>(queryParam);
        //剔除不参与签名运算的 signature
        treeMap.remove("signature");
        //生成 baseString
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : treeMap.entrySet()) {
            String value = entry.getValue();
            //参数值为空的不参与签名，
            if (value != null && !value.isEmpty()) {
                //参数值需要 URLEncode
                String encode = URLEncoder.encode(value, StandardCharsets.UTF_8.name());
                builder.append(entry.getKey()).append("=").append(encode).append("&");
            }
        }
        //删除最后位的&符号
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        String baseString = builder.toString();
        System.out.println("baseString：" + baseString);
        Mac mac = Mac.getInstance("HmacSHA1");
        SecretKeySpec keySpec = new SecretKeySpec(accessKeySecret.getBytes(StandardCharsets.UTF_8),
                StandardCharsets.UTF_8.name());
        mac.init(keySpec);
        //得到签名 byte[]
        byte[] signBytes = mac.doFinal(baseString.getBytes(StandardCharsets.UTF_8));
        //将 byte[]base64 编码
        return Base64.getEncoder().encodeToString(signBytes);
    }

    /**
     * @param accessKeySecret
     * @param queryParam
     * @return
     * @throws Exception
     */
    public static String baseString(String accessKeySecret, Map<String, String> queryParam) throws Exception {
        //排序
        TreeMap<String, String> treeMap = new TreeMap<>(queryParam);
        //剔除不参与签名运算的 signature
        treeMap.remove("signature");
        //生成 baseString
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : treeMap.entrySet()) {
            String value = entry.getValue();
            //参数值为空的不参与签名，
            if (value != null && !value.isEmpty()) {
                //参数值需要 URLEncode
                String encode = URLEncoder.encode(value, StandardCharsets.UTF_8.name());
                builder.append(entry.getKey()).append("=").append(encode).append("&");
            }
        }
        //删除最后位的&符号
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }


}
