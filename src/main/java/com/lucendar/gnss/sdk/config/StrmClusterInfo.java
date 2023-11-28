/*******************************************************************************
 *  Copyright (c) 2019, 2021 lucendar.com.
 *  All rights reserved.
 *
 *  Contributors:
 *     KwanKin Yau (alphax@vip.163.com) - initial API and implementation
 *******************************************************************************/
package com.lucendar.gnss.sdk.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class StrmClusterInfo implements Cloneable {

    private List<StrmServerInfo> serverList = new ArrayList<>();

    public List<StrmServerInfo> getServerList() {
        return serverList;
    }

    public void setServerList(List<StrmServerInfo> serverList) {
        this.serverList = serverList;
    }

    public StrmServerInfo find(String instanceId) {
        if (this.serverList == null)
            return null;

        for (StrmServerInfo info : this.serverList)
            if (Objects.equals(info.getConfig().getInstanceId(), instanceId))
                return info;

        return null;
    }

    public void assign(StrmMediaClusterConfig clusterConfig) {
        serverList.clear();

        for (StrmMediaConfig config : clusterConfig.getList()) {
            StrmMediaConfig cfg = config.clone();
            StrmServerInfo info = new StrmServerInfo();
            info.setConfig(cfg);

            serverList.add(info);
        }
    }

    public void assignFrom(StrmClusterInfo source) {
        List<StrmServerInfo> serverList = source.serverList;
        if (serverList != null) {
            this.serverList = new ArrayList<>();
            for (StrmServerInfo info : source.serverList) {
                this.serverList.add(info.clone());
            }
        } else
            this.serverList = null;
    }

    public static StrmClusterInfo init(StrmMediaClusterConfig clusterConfig) {
        StrmClusterInfo r = new StrmClusterInfo();
        r.assign(clusterConfig);
        return r;
    }

    @Override
    public StrmClusterInfo clone() {
        StrmClusterInfo r = new StrmClusterInfo();
        r.assignFrom(this);
        return r;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", StrmClusterInfo.class.getSimpleName() + "[", "]")
                .add("serverList=" + serverList)
                .toString();
    }
}
