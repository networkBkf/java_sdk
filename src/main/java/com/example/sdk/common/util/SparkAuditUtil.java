package com.example.sdk.common.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.TreeMap;

/**
 * | Author: kfbi2
 * | Date: 2024/05/08 15:20
 * | Desc: 星火大模型审核工具类
 * | Version: 1.0.0
 * | Update:
 */
public class SparkAuditUtil {

    /**
     * @param queryParam
     * @param accessKeySecret
     * @return
     * @throws Exception
     */
    public static String urlEncode(Map<String, String> queryParam, String accessKeySecret) throws Exception {
        String urlPrefix = wrapperWithoutSignature(queryParam);
        String signature = signature(accessKeySecret, urlPrefix);
        return urlPrefix + "&signature=" + signature;
    }

    /**
     * 组装参数，剔除signature
     *
     * @param queryParam
     * @return
     * @throws Exception
     */
    private static String wrapperWithoutSignature(Map<String, String> queryParam) throws Exception {
        TreeMap<String, String> treeMap = new TreeMap<>(queryParam);
        treeMap.remove("signature");
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : treeMap.entrySet()) {
            String value = entry.getValue();
            if (value != null && !value.isEmpty()) {
                String encode = URLEncoder.encode(value, StandardCharsets.UTF_8.name());
                builder.append(entry.getKey()).append("=").append(encode).append("&");
            }
        }
        if (builder.length() > 0) builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    /**
     * 生成验签签名
     *
     * @param accessKeySecret
     * @param baseString
     * @return
     * @throws Exception
     */
    private static String signature(String accessKeySecret, String baseString) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA1");
        SecretKeySpec keySpec = new SecretKeySpec(accessKeySecret.getBytes(StandardCharsets.UTF_8),
                StandardCharsets.UTF_8.name());
        mac.init(keySpec);
        byte[] signBytes = mac.doFinal(baseString.getBytes(StandardCharsets.UTF_8));
        return URLEncoder.encode(Base64.getEncoder().encodeToString(signBytes), StandardCharsets.UTF_8.name());
    }

}
