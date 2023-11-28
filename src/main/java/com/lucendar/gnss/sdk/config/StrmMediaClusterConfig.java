/*******************************************************************************
 *  Copyright (c) 2019, 2021 lucendar.com.
 *  All rights reserved.
 *
 *  Contributors:
 *     KwanKin Yau (alphax@vip.163.com) - initial API and implementation
 *******************************************************************************/
package com.lucendar.gnss.sdk.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class StrmMediaClusterConfig {

    private static class ClusterConfig {

        /**
         * Key: instanceId
         */
        private Map<String, StrmMediaConfig> map = new TreeMap<>();
        private List<StrmMediaConfig> list = new ArrayList<>();

        public List<StrmMediaConfig> getList() {
            return new ArrayList<>(map.values());
        }

        private void clear() {
            map.clear();
            list.clear();
        }

        private void put(StrmMediaConfig c) {
            map.put(c.getInstanceId(), c);
            list.add(c);
        }

        public StrmMediaConfig getByInstanceId(String instanceId) {
            return map.get(instanceId);
        }


        public void set(Collection<StrmMediaConfig> configs) {
            clear();
            configs.forEach(this::put);
        }

        public void set(StrmMediaConfig[] configs) {
            clear();
            for (StrmMediaConfig c : configs)
                put(c);
        }

        public int size() {
            return list.size();
        }

        public StrmMediaConfig getByIndex(int index) {
            return list.get(index);
        }
    }

    private final AtomicReference<ClusterConfig> configRef = new AtomicReference<>(new ClusterConfig());
    private final AtomicInteger lastIndex = new AtomicInteger(0);

    public StrmMediaConfig getByInstanceId(String instanceId) {
        ClusterConfig config = configRef.get();
        if (config == null)
            return null;

        StrmMediaConfig r = config.getByInstanceId(instanceId);
        if (r != null)
            return r.clone();
        else
            return null;
    }

    public void set(Collection<StrmMediaConfig> clusterConfig) {
        List<StrmMediaConfig> list = new ArrayList<>();
        for (StrmMediaConfig config : clusterConfig) {
            if (!config.isDisabled())
                list.add(config);
        }

        ClusterConfig config = new ClusterConfig();
        config.set(list);

        configRef.set(config);
    }

    public void set(StrmMediaConfig[] clusterConfig) {
        List<StrmMediaConfig> list = new ArrayList<>();
        for (StrmMediaConfig config : clusterConfig) {
            if (!config.isDisabled())
                list.add(config);
        }

        ClusterConfig config = new ClusterConfig();
        config.set(list);

        configRef.set(config);
    }

    public List<StrmMediaConfig> getList() {
        ClusterConfig config = configRef.get();
        return config.getList();
    }

    public StrmMediaConfig acquire() {
        ClusterConfig config = configRef.get();
        int sz = config.size();
        if (sz == 0)
            return null;

        int idx = lastIndex.getAndIncrement();
        idx = idx % sz;
        return config.getByIndex(idx);
    }



}
