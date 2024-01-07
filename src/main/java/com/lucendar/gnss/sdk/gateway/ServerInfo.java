package com.lucendar.gnss.sdk.gateway;

import java.util.Arrays;
import java.util.Map;
import java.util.StringJoiner;

public class ServerInfo {
    private String ver;
    private long startTm;
    private String instance;
    private String[] features;
    private Map<String, String> config;

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public long getStartTm() {
        return startTm;
    }

    public void setStartTm(long startTm) {
        this.startTm = startTm;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String[] getFeatures() {
        return features;
    }

    public void setFeatures(String[] features) {
        this.features = features;
    }

    public Map<String, String> getConfig() {
        return config;
    }

    public void setConfig(Map<String, String> config) {
        this.config = config;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ServerInfo.class.getSimpleName() + "[", "]")
                .add("ver='" + ver + "'")
                .add("startTm=" + startTm)
                .add("instance='" + instance + "'")
                .add("features=" + Arrays.toString(features))
                .add("config=" + config)
                .toString();
    }
}
