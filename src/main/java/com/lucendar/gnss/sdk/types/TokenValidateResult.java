package com.lucendar.gnss.sdk.types;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.StringJoiner;

public class TokenValidateResult {

    public static final Type TYPE = new TypeToken<TokenValidateResult>(){}.getType();

    private String appId;
    private String userName;

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

    @Override
    public String toString() {
        return new StringJoiner(", ", TokenValidateResult.class.getSimpleName() + "[", "]")
                .add("appId='" + appId + "'")
                .add("userName='" + userName + "'")
                .toString();
    }
}
