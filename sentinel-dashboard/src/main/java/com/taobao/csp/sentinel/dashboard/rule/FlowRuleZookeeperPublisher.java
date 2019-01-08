package com.taobao.csp.sentinel.dashboard.rule;

import com.alibaba.fastjson.JSON;
import com.taobao.csp.sentinel.dashboard.config.ZookeeperConfig;
import com.taobao.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.taobao.csp.sentinel.dashboard.discovery.AppManagement;
import com.taobao.csp.sentinel.dashboard.util.ZookeeperUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("flowRuleZookeeperPublisher")
public class FlowRuleZookeeperPublisher implements DynamicRulePublisher<List<FlowRuleEntity>> {

    @Autowired
    private AppManagement appManagement;

    @Autowired
    private ZookeeperConfig zookeeperConfig;

    @Override
    public void publish(String app, List<FlowRuleEntity> rules) throws Exception {
        CuratorFramework zkClient = zookeeperConfig.getZkClient();
        String path = ZookeeperUtil.getPath(app,"flow-rules");
        Stat stat = zkClient.checkExists().forPath(path);
        if (stat == null) {
            zkClient.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, null);
        }
        zkClient.setData().forPath(path, JSON.toJSONBytes(rules));
    }
}
