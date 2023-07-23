package com.lucendar.gnss.sdk.session;

import com.google.gson.reflect.TypeToken;
import info.gratour.common.types.rest.Reply;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;
import java.util.StringJoiner;

public class LoginResult {
    public static final Type REPLY_TYPE = new TypeToken<Reply<LoginResult>>(){}.getType();
    private String authToken;
    private String ver;
    private String[] features;
    private Map<String, String> config;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
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
        return new StringJoiner(", ", LoginResult.class.getSimpleName() + "[", "]")
                .add("authToken='" + authToken + "'")
                .add("ver='" + ver + "'")
                .add("features=" + Arrays.toString(features))
                .add("config=" + config)
                .toString();
    }
}
