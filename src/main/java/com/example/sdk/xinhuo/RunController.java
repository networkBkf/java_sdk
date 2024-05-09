package com.example.sdk.xinhuo;

import com.example.sdk.common.util.SparkAuditUtil;
import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;
import java.util.UUID;

/**
 * | Author: kfbi2
 * | Date: 2024/04/12 15:47
 * | Desc: 调用星火服务接口
 * | Version: 1.0.0
 * | Update:
 */
@RestController
public class RunController {

    @Resource
    private RestTemplate restTemplate;

    private String URI = "http://172.31.164.82/audit/v2/";

    /**
     * @param url
     * @param appId
     * @param accessKeyId
     * @param accessKeySecret
     * @return
     * @throws Exception
     */
    @PostMapping("/common")
    public String commonUrlEncoder(String url, String appId, String accessKeyId, String accessKeySecret) throws Exception {
        Map<String, String> queryMap = getQueryMap(appId, accessKeyId);
        String urlEncode = SparkAuditUtil.urlEncode(queryMap, accessKeySecret);
        return URI + url + "?" + urlEncode;
    }

    private static Map<String, String> getQueryMap(String appId, String accessKeyId) {
        Map<String, String> queryMap = Maps.newHashMap();
        queryMap.put("utc", ContentApprovalTest.generateUTC());
        queryMap.put("appId", appId);
        queryMap.put("accessKeyId", accessKeyId);
        String uuid = UUID.randomUUID().toString().replace("-", "");
        queryMap.put("uuid", uuid);
        return queryMap;
    }

}
