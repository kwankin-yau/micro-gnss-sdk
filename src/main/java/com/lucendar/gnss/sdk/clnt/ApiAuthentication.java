package com.lucendar.gnss.sdk.clnt;

import okhttp3.Request;

public interface ApiAuthentication {
    Request.Builder handle(Request.Builder builder);
}
