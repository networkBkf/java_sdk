package com.example.sdk.pulsar;

import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.common.policies.data.TenantInfo;

/**
 * | Author: kfbi2
 * | Date: 2024/04/10 14:39
 * | Desc: 测试pulsar相关功能
 * | Version: 1.0.0
 * | Update:
 */
public class PulsarTest {

    public static void main(String[] args) throws Exception {
        PulsarAdmin pulsarAdmin = PulsarAdmin.builder().serviceHttpUrl("http://172.31.164.82:8090,172.31.164.82:8091,172.31.164.82:8092").build();
//        pulsarAdmin.tenants().createTenant("kfbi4",
//                TenantInfo.builder()
//                        .allowedClusters(
//                                new java.util.HashSet<String>() {
//                                    {
//                                        add("pulsar-cluster-1");
//                                    }
//                                }
//                        )
//                        .build()
//                );
//        pulsarAdmin.namespaces().createNamespace("kfbi4/kfbi4_n");
        pulsarAdmin.topics().createPartitionedTopic("kfbi4/kfbi4_n/kfbi4_t", 3);
        pulsarAdmin.close();
    }


}
