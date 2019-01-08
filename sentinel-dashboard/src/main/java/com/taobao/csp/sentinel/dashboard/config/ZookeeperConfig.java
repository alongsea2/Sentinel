package com.taobao.csp.sentinel.dashboard.config;

import org.apache.commons.lang.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ZookeeperConfig implements InitializingBean{

    @Value(value = "${zk.server}")
    private String zkServer;

    private CuratorFramework zkClient;

    public CuratorFramework getZkClient() {
        if(zkClient == null){
            startConnectToZk();
        }
        if(zkClient.getState().name().equals(CuratorFrameworkState.STOPPED.name())){
            zkClient = null;
            startConnectToZk();
        }
        return zkClient;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(StringUtils.isBlank(zkServer)){
            throw new IllegalArgumentException("zk server is not defined please check");
        }
        startConnectToZk();
    }

    public void startConnectToZk(){
        this.zkClient = CuratorFrameworkFactory.newClient(zkServer, new ExponentialBackoffRetry(1000, 3));
        zkClient.start();
    }

    public void stopConnectToZk(){
        if(zkClient != null){
            zkClient.close();
            zkClient = null;
        }
    }
}
