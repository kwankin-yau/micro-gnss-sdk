package com.lucendar.gnss.sdk;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lucendar.gnss.sdk.session.GnssLoginReq;
import com.lucendar.gnss.sdk.session.GnssLoginResult;
import com.lucendar.gnss.sdk.strm.GnssOpenLiveStrmReq;
import com.lucendar.gnss.sdk.strm.GnssOpenReplayStrmReq;
import com.lucendar.gnss.sdk.strm.GnssOpenStrmResult;
import com.lucendar.gnss.sdk.types.GnssApiConnParams;
import com.lucendar.strm.common.strm.LiveStrmCtrlReq;
import com.lucendar.strm.common.strm.ReleaseStrmsReq;
import com.lucendar.strm.common.strm.ReplayStrmCtrlReq;
import info.gratour.common.error.ErrorWithCode;
import info.gratour.common.types.rest.Reply;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.Base64;
import java.util.UUID;

import static com.lucendar.gnss.sdk.HttpConsts.HEADER_X_APP_ID;
import static com.lucendar.gnss.sdk.HttpConsts.HEADER_X_AUTH_TOKEN;
import static info.gratour.common.types.rest.Reply.RAW_REPLY_TYPE;

public class GnssClient {


    public static final Logger LOGGER = LoggerFactory.getLogger(GnssClient.class);

    public static final GsonBuilder GSON_BUILDER = new GsonBuilder();
    public static final Gson GSON = GSON_BUILDER.create();

    public static final GsonBuilder GSON_BUILDER_PRETTY = GSON_BUILDER.setPrettyPrinting();
    public static final Gson GSON_PRETTY = GSON_BUILDER_PRETTY.create();

    private final MediaType JSON = MediaType.get("application/json");

    protected OkHttpClient createHttpClient(boolean logging) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(Duration.ofSeconds(15));

        if (logging) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(@NotNull String s) {
                    LOGGER.info(s);
                }
            });
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }

        return builder.build();
    }

    protected final OkHttpClient httpClient;
    protected String token;
    protected final GnssApiConnParams connParams;

    protected final String authHeaderValue;

    /**
     * @param connParams
     * @param logging      whether logging the http call
     */
    public GnssClient(
            GnssApiConnParams connParams,
            boolean logging) {

        this.connParams = connParams;

        httpClient = createHttpClient(logging);
        authHeaderValue = connParams.authorizationHeaderValue();
    }

    public GnssApiConnParams getConnParams() {
        return connParams;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }

    protected <T extends Reply<?>> T makeCall(String endPoint, Request request, Type responseType, boolean checkResp) {
        try (Response resp = httpClient.newCall(request).execute()) {
            ResponseBody body = resp.body();
            if (body != null) {
                int httpCategory = resp.code() / 100;

                // TODO: what about status code category is 3xx?
                if (httpCategory != 2) {
                    String message = "API `%s` call return status: %d.";
                    message = String.format(message, endPoint, resp.code());
                    throw new RuntimeException(message);
                }

                String s = body.string();
                T r = GSON.fromJson(s, responseType);
                if (checkResp && !r.ok())
                    throw new ErrorWithCode(r.getErrCode(), r.getMessage());

                return r;
            } else {
                String message = "API `%s` call return empty body: " + resp;
                message = String.format(message, endPoint);
                throw new RuntimeException(message);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected Request buildRequest(String endPoint, String method, RequestBody body) {
        Request.Builder builder = new Request.Builder()
                .url(connParams.getApiBasePath() + endPoint)
                .header(
                        HttpHeaders.AUTHORIZATION,
                        authHeaderValue
                );

        if (token != null)
            builder.header(HEADER_X_AUTH_TOKEN, token);

        if (connParams.getAppId() != null)
            builder.header(HEADER_X_APP_ID, connParams.getAppId());

        if (body == null &&
                (method.equalsIgnoreCase("POST") || (method.equalsIgnoreCase("PUT")))
        )
            body = RequestBody.create("{}", JSON);

        Request r = builder.method(method, body).build();
        System.out.println(r.toString());
        return r;
    }

    protected <T extends Reply<?>> T callWithoutBody(
            String endPoint,
            Type responseType,
            String method,
            boolean checkResp) {
        Request request = buildRequest(endPoint, method, null);
        return makeCall(endPoint, request, responseType, checkResp);
    }

    protected <T extends Reply<?>> T callWithBody(
            String endPoint,
            Object reqBody,
            Type responseType,
            String method,
            boolean checkResp) {
        String json = GSON.toJson(reqBody);

        Request request = buildRequest(endPoint, method, RequestBody.create(json, JSON));
        return makeCall(endPoint, request, responseType, checkResp);
    }

    protected <T extends Reply<?>> T postWithBody(String endPoint, Object reqBody, Type responseType, boolean checkResp) {
        return callWithBody(endPoint, reqBody, responseType, "POST", checkResp);
    }

    protected <T extends Reply<?>> T postWithBody(String endPoint, Object reqBody, Type responseType) {
        return postWithBody(endPoint, reqBody, responseType, true);
    }

    protected <T extends Reply<?>> T putWithBody(String endPoint, Object reqBody, Type responseType, boolean checkResp) {
        return callWithBody(endPoint, reqBody, responseType, "PUT", checkResp);
    }

    protected <T extends Reply<?>> T putWithBody(String endPoint, Object reqBody, Type responseType) {
        return putWithBody(endPoint, reqBody, responseType, true);
    }

    protected Reply<Void> postReturnRawReply(String endPoint, Object reqBody, boolean checkResp) {
        return postWithBody(endPoint, reqBody, RAW_REPLY_TYPE, checkResp);
    }

    protected Reply<Void> postReturnRawReply(String endPoint, Object reqBody) {
        return postReturnRawReply(endPoint, reqBody, true);
    }

    protected Reply<Void> postWithoutBodyReturnRawReply(String endPoint, boolean checkResp) {
        return callWithoutBody(endPoint, RAW_REPLY_TYPE, "POST", checkResp);
    }

    protected Reply<Void> postWithoutBodyReturnRawReply(String endPoint) {
        return postWithoutBodyReturnRawReply(endPoint, true);
    }

    protected Reply<Void> putReturnRawReply(String endPoint, Object reqBody) {
        return putWithBody(endPoint, reqBody, RAW_REPLY_TYPE, true);
    }

    protected <T extends Reply<?>> T postWithoutBody(
            String endPoint,
            Type responseType,
            boolean checkResp) {
        return callWithoutBody(endPoint, responseType, "POST", checkResp);
    }

    protected <T extends Reply<?>> T postWithoutBody(
            String endPoint,
            Type responseType) {
        return postWithoutBody(endPoint, responseType, true);
    }

    protected <T extends Reply<?>> T putWithoutBody(
            String endPoint,
            Type responseType,
            boolean checkResp) {
        return callWithoutBody(endPoint, responseType, "PUT", checkResp);
    }

    protected <T extends Reply<?>> T putWithoutBody(
            String endPoint,
            Type responseType) {
        return putWithoutBody(endPoint, responseType, true);
    }

    /**
     * 登录。
     *
     * @return 登录结果。
     */
    public GnssLoginResult login() {
        GnssLoginReq req = new GnssLoginReq();
        req.setAppId(connParams.getAppId());
        req.setUserName(connParams.getUsername());
        req.setPassword(connParams.getPassword());
        if (token != null)
            req.setToken(token);

        Reply<GnssLoginResult> reply = postWithBody("/login", req, GnssLoginResult.REPLY_TYPE);
        GnssLoginResult r = reply.first();

        if (token == null)
            token = r.getAuthToken();

        return r;
    }

    /**
     * 注销。使当前使用的token失效。已经打开的、未关闭的媒体流请求将全部被关闭。
     */
    public void logout() {
        if (token == null)
            return;

        try {
            postWithoutBodyReturnRawReply("/logout", false);
        } catch (Throwable t) {
            // suppress exception
        }

        token = null;
    }

    /**
     * 打开实时音视频。
     *
     * @param req 实时音视频流请求。
     * @return 打开流请求返回对象。
     */
    public GnssOpenStrmResult openLive(GnssOpenLiveStrmReq req) {
        Reply<GnssOpenStrmResult> r = postWithBody("/strm/live/open", req, GnssOpenStrmResult.REPLY_TYPE);
        return r.first();
    }

    /**
     * 实时音视频控制。
     *
     * @param req 实时音视频控制请求。
     * @return 请求响应。
     */
    public Reply<Void> liveCtrl(LiveStrmCtrlReq req) {
        return postReturnRawReply("/strm/live/ctrl", req);
    }

    /**
     * 打开远程录像回放。
     *
     * @param req 远程录像回放请求。
     * @return 打开流请求返回对象。
     */
    public GnssOpenStrmResult openReplay(GnssOpenReplayStrmReq req) {
        Reply<GnssOpenStrmResult> r = postWithBody("/strm/replay/open", req, GnssOpenStrmResult.REPLY_TYPE);
        return r.first();
    }

    /**
     * 远程录像回放控制。
     *
     * @param req 远程录像回放控制请求。
     * @return 请求响应。
     */
    public Reply<Void> replayCtrl(ReplayStrmCtrlReq req) {
        return postReturnRawReply("/strm/replay/ctrl", req);
    }

    /**
     * 关闭媒体请求。
     *
     * @param req 关闭媒体请求对象。
     */
    public void closeStrms(ReleaseStrmsReq req) {
        try {
            postReturnRawReply("/strm/close", req, false);
        } catch (Throwable t) {
            // suppress exception, mainly for network exception
        }
    }

    /**
     * 关闭单个媒体请求。
     *
     * @param reqId 所要关闭的媒体请求ID
     */
    public void closeStrm(String reqId) {
        ReleaseStrmsReq r = new ReleaseStrmsReq();
        r.setReqIds(new String[]{reqId});

        closeStrms(r);
    }

    /**
     * 创建一个令牌。
     *
     * @return 新的令牌字符串。
     */
    public static String newToken() {
        byte[] bytes = new byte[16];
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        UUID uuid = UUID.randomUUID();
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return Base64.getUrlEncoder().encodeToString(bytes);
    }

}
