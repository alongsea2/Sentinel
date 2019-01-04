package com.taobao.csp.sentinel.dashboard.rule;

import com.alibaba.fastjson.JSON;
import com.taobao.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.taobao.csp.sentinel.dashboard.discovery.AppManagement;
import com.taobao.csp.sentinel.dashboard.util.ZookeeperUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("flowRuleZookeeperPublisher")
public class FlowRuleZookeeperPublisher implements DynamicRulePublisher<List<FlowRuleEntity>> {

    @Autowired
    private AppManagement appManagement;

    @Override
    public void publish(String app, List<FlowRuleEntity> rules) throws Exception {
        CuratorFramework zkClient = CuratorFrameworkFactory.newClient("172.16.249.115:2181", new ExponentialBackoffRetry(1000, 3));
        zkClient.start();
        String path = ZookeeperUtil.getPath(app,"test");
        Stat stat = zkClient.checkExists().forPath(path);
        if (stat == null) {
            zkClient.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, null);
        }
        zkClient.setData().forPath(path, JSON.toJSONBytes(rules));
        zkClient.close();
    }
}
