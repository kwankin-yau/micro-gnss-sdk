package com.lucendar.gnss.sdk.session;

import com.google.gson.reflect.TypeToken;
import info.gratour.common.types.rest.Reply;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;
import java.util.StringJoiner;

public class GnssLoginResult {
    public static final Type REPLY_TYPE = new TypeToken<Reply<GnssLoginResult>>(){}.getType();

    public static class Features {
        public static final String FEATURE__EXTERNAL_GATEWAY = "externalGateway";
        public static final String FEATURE__ONLINE_TERMS_QRY_DISABLED = "onlineTermsQryDisabled";

        /**
         * A/V attribute query is supported.
         */
        public static final String FEATURE__AV_ATTRS_QRY = "avAttrsQry";
    }

    private String authToken;
    private String ver;
    private String instance;
    private String[] features;
    private Map<String, String> config;

    public GnssLoginResult() {
    }

    public GnssLoginResult(String authToken, String ver, String instance, String[] features, Map<String, String> config) {
        this.authToken = authToken;
        this.ver = ver;
        this.instance = instance;
        this.features = features;
        this.config = config;
    }

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
        return new StringJoiner(", ", GnssLoginResult.class.getSimpleName() + "[", "]")
                .add("authToken='" + authToken + "'")
                .add("ver='" + ver + "'")
                .add("instance='" + instance + "'")
                .add("features=" + Arrays.toString(features))
                .add("config=" + config)
                .toString();
    }
}
