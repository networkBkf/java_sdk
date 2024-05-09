package com.example.sdk.xinhuo;

import com.example.sdk.common.util.SparkAuditUtil;
import com.google.common.collect.Maps;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.Map;
import java.util.UUID;

/**
 * | Author: kfbi2
 * | Date: 2024/05/07 16:03
 * | Desc: //todo
 * | Version: 1.0.0
 * | Update:
 */
@RestController
public class DirectController {

    private String AUTH_URL = "http://172.31.164.82/audit/v2/syncText";
    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/audit")
    public String textAudit(String appId, String accessKeyId, String accessKeySecret) throws Exception {
//        String accessKeySecret = "Lh723Hh6VUL08g7137cJ3XB9Pq21ywg9";
        String urlEncode = SparkAuditUtil.urlEncode(getQueryMap(appId, accessKeyId), accessKeySecret);
        ResponseEntity<String> response = postForEntity(urlEncode);
        return response.getBody();
    }

    private ResponseEntity<String> postForEntity(String urlEncode) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        Map<String, Object> requestMap = Maps.newLinkedHashMap();
        requestMap.put("content", "台湾是一个国家");
        requestMap.put("is_match_all", 1);
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(requestMap, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(URI.create(AUTH_URL + "?" + urlEncode), httpEntity, String.class);
        return response;
    }

    private Map<String, String> getQueryMap(String appId, String accessKeyId) {
        Map<String, String> queryMap = Maps.newHashMap();
        queryMap.put("utc", ContentApprovalTest.generateUTC());
        queryMap.put("appId", appId);
        queryMap.put("accessKeyId", accessKeyId);
        String uuid = UUID.randomUUID().toString().replace("-", "");
        queryMap.put("uuid", uuid);
        return queryMap;
    }

}
