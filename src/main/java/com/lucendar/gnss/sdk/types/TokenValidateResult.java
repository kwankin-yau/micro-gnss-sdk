package com.lucendar.gnss.sdk.types;

import com.google.gson.reflect.TypeToken;
import com.lucendar.strm.common.StreamingApi;

import java.lang.reflect.Type;
import java.util.StringJoiner;

public class TokenValidateResult {

    public static final Type TYPE = new TypeToken<TokenValidateResult>(){}.getType();

    private String appId;
    private String userName;

    public TokenValidateResult() {
    }

    public TokenValidateResult(String appId, String userName) {
        this.appId = appId;
        this.userName = userName;
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

    public String appIdDef() {
        if (appId != null)
            return appId;

        return StreamingApi.DEFAULT_APP_ID;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TokenValidateResult.class.getSimpleName() + "[", "]")
                .add("appId='" + appId + "'")
                .add("userName='" + userName + "'")
                .toString();
    }
}
