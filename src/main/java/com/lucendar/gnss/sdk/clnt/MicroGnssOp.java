/*
 * Copyright (c) 2024 lucendar.com.
 * All rights reserved.
 */
package com.lucendar.gnss.sdk.clnt;

import com.lucendar.gnss.sdk.GnssConsts;
import com.lucendar.gnss.sdk.session.GnssLoginReq;
import com.lucendar.gnss.sdk.session.GnssLoginResult;
import com.lucendar.gnss.sdk.strm.GnssOpenLiveStrmReq;
import com.lucendar.gnss.sdk.strm.GnssOpenStrmResult;
import com.lucendar.gnss.sdk.types.ReplyTypes;
import com.lucendar.strm.common.strm.KeepStrmReq;
import com.lucendar.strm.common.strm.KeepStrmReqResult;
import com.lucendar.strm.common.strm.ReleaseStrmsReq;
import info.gratour.common.types.rest.Reply;
import okhttp3.OkHttpClient;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class MicroGnssOp {

    private final ApiClient apiClient;
    private final ReqHooker reqHooker;
    private final TokenAuthentication tokenAuthentication = new TokenAuthentication();
    private final String appId;
    private final String username;
    private final String password;

    public MicroGnssOp(@NonNull OkHttpClient httpClient, @NonNull String endPointUrl, @Nullable ReqHooker reqHooker,
                       @NonNull String appId, @NonNull String username, @NonNull String password) {
        this.appId = appId;
        this.username = username;
        this.password = password;
        this.apiClient = new SimpleApiClient(GnssConsts.GSON, endPointUrl, tokenAuthentication, httpClient);
        this.reqHooker = reqHooker;
    }

    protected <T> Reply<T> checkReply(Reply<T> reply) {
        if (reply.ok()) {
            return reply;
        } else {
            throw reply.toErrorWithCode();
        }
    }

    public Reply<GnssLoginResult> login(GnssLoginReq req) {
        Reply<GnssLoginResult> r = checkReply(apiClient.post("/login", GnssLoginResult.REPLY_TYPE, reqHooker, req));
        tokenAuthentication.setAuthToken(r.first().getAuthToken());
        return r;
    }

    public void sessionRequired() {
        if (!tokenAuthentication.hasAuthToken()) {
            GnssLoginReq lr = new GnssLoginReq(appId, username, password);
            login(lr);
        }
    }

    @NonNull
    public Reply<GnssOpenStrmResult> liveOpen(@NonNull GnssOpenLiveStrmReq req) {
        sessionRequired();
        return checkReply(apiClient.post("/strm/live/open", GnssOpenStrmResult.REPLY_TYPE, reqHooker, req));
    }

    @NonNull
    public Reply<Void> releaseStrm(@NonNull String reqId) {
        sessionRequired();
        ReleaseStrmsReq req = new ReleaseStrmsReq(reqId);
        return checkReply(apiClient.postRaw("strm/close", reqHooker, req));
    }

    @NonNull
    public Reply<KeepStrmReqResult> keepStrm(@NonNull String reqId) {
        sessionRequired();
        KeepStrmReq req = new KeepStrmReq(reqId);
        return checkReply(apiClient.post("/strm/keep", ReplyTypes.REPLY_TYPE__KEEP_STRM_RESULT, reqHooker, req));
    }

}
