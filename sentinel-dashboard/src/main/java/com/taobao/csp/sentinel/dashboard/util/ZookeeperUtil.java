package com.taobao.csp.sentinel.dashboard.util;

import org.apache.zookeeper.ZKUtil;

public class ZookeeperUtil extends ZKUtil{

    public static String getPath(String groupId, String dataId) {
        String path = "";
        if (groupId.startsWith("/")) {
            path += groupId;
        } else {
            path += "/" + groupId;
        }
        if (dataId.startsWith("/")) {
            path += dataId;
        } else {
            path += "/" + dataId;
        }
        return path;
    }
}
