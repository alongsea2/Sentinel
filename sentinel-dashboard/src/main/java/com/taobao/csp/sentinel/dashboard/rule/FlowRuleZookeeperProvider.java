package com.taobao.csp.sentinel.dashboard.rule;

import com.alibaba.fastjson.JSON;
import com.taobao.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.taobao.csp.sentinel.dashboard.util.ZookeeperUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZKUtil;
import org.apache.zookeeper.data.Stat;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("flowRuleZookeeperProvider")
public class FlowRuleZookeeperProvider implements DynamicRuleProvider<List<FlowRuleEntity>>{

    @Override
    public List<FlowRuleEntity> getRules(String appName) throws Exception {
        CuratorFramework zkClient = CuratorFrameworkFactory.newClient("172.16.249.115:2181", new ExponentialBackoffRetry(1000, 3));
        zkClient.start();
        String path = ZookeeperUtil.getPath(appName,"test");
        List<FlowRuleEntity> list = JSON.parseArray(new String(zkClient.getData().forPath(path)), FlowRuleEntity.class);
        zkClient.close();
        return list;
    }
}
