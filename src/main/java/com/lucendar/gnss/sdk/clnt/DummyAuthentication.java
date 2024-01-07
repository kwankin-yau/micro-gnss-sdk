package com.lucendar.gnss.sdk.clnt;

import okhttp3.Request;

public class DummyAuthentication implements ApiAuthentication{
    public static final DummyAuthentication INSTANCE = new DummyAuthentication();
    @Override
    public Request.Builder handle(Request.Builder builder) {
        return builder;
    }
}
