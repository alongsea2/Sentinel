package com.taobao.csp.sentinel.dashboard.rule;

import com.alibaba.fastjson.JSON;
import com.taobao.csp.sentinel.dashboard.config.ZookeeperConfig;
import com.taobao.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.taobao.csp.sentinel.dashboard.util.ZookeeperUtil;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("flowRuleZookeeperProvider")
public class FlowRuleZookeeperProvider implements DynamicRuleProvider<List<FlowRuleEntity>>{

    @Autowired
    private ZookeeperConfig zookeeperConfig;

    @Override
    public List<FlowRuleEntity> getRules(String appName) throws Exception {
        CuratorFramework zkClient = zookeeperConfig.getZkClient();
        String path = ZookeeperUtil.getPath(appName,"flow-rules");
        return JSON.parseArray(new String(zkClient.getData().forPath(path)), FlowRuleEntity.class);
    }
}
