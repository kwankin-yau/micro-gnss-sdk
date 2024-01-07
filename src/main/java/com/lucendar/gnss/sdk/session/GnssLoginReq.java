package com.lucendar.gnss.sdk.session;

import org.jetbrains.annotations.NotNull;

import java.util.StringJoiner;

public class GnssLoginReq {
    private String appId;
    private String userName;
    private String password;

    public GnssLoginReq() {
    }

    public GnssLoginReq(@NotNull String appId, @NotNull String userName, @NotNull String password) {
        this.appId = appId;
        this.userName = userName;
        this.password = password;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", GnssLoginReq.class.getSimpleName() + "[", "]")
                .add("appId='" + appId + "'")
                .add("userName='" + userName + "'")
                .add("password='" + password + "'")
                .toString();
    }
}
