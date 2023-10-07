package com.lucendar.gnss.sdk.types;

import org.springframework.http.HttpHeaders;

import java.nio.charset.StandardCharsets;
import java.util.StringJoiner;

public class GnssApiConnParams {

    private final String apiBasePath;
    private final String appId;
    private final String username;
    private final String password;

    public GnssApiConnParams(String apiBasePath, String appId, String username, String password) {
        if (apiBasePath.endsWith("/"))
            this.apiBasePath = apiBasePath.substring(0, apiBasePath.length()-1);
        else
            this.apiBasePath = apiBasePath;
        this.appId = appId;
        this.username = username;
        this.password = password;
    }

    public String getApiBasePath() {
        return apiBasePath;
    }

    public String getAppId() {
        return appId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String authorizationHeaderValue() {
        return "Basic " + HttpHeaders.encodeBasicAuth(username, password, StandardCharsets.UTF_8);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", GnssApiConnParams.class.getSimpleName() + "[", "]")
                .add("apiBasePath='" + apiBasePath + "'")
                .add("appId='" + appId + "'")
                .add("username='" + username + "'")
                .add("password='" + password + "'")
                .toString();
    }
}
