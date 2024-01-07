package com.lucendar.gnss.sdk.clnt;

import okhttp3.Request;

public class TokenAuthentication implements ApiAuthentication {

    private volatile String authToken;

    public boolean hasAuthToken() {
        return authToken != null;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    @Override
    public Request.Builder handle(Request.Builder builder) {
        if (authToken != null)
            builder.addHeader("X-Auth-Token", authToken);

        return builder;
    }
}
