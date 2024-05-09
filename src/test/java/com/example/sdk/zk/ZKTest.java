package com.example.sdk.zk;

import org.apache.curator.framework.CuratorFramework;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

/**
 * | Author: kfbi2
 * | Date: 2024/04/17 14:11
 * | Desc: //todo
 * | Version: 1.0.0
 * | Update:
 */
@SpringBootTest
public class ZKTest {

    @Autowired
    CuratorFramework curatorFramework;

    @Test
    public void test() throws Exception {
//        String pathWithParent = "/managed-ledgers";
//        curatorFramework.delete().guaranteed().deletingChildrenIfNeeded().forPath(pathWithParent);
        System.out.println(UUID.randomUUID().toString().replaceAll("-",""));
        String a = "\"{\"l1\":\"political\",\"l2\":\"politicalEvents\",\"rating\":-1}\"";
    }

}
