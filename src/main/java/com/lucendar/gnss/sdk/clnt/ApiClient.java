package com.lucendar.gnss.sdk.clnt;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.lucendar.common.types.KV;
import com.lucendar.common.types.LazyVar;
import com.lucendar.common.types.rest.Pagination;
import info.gratour.common.error.ErrorWithCode;
import info.gratour.common.error.Errors;
import info.gratour.common.types.rest.Reply;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * The HTTP Api Client, must be thread-safe.
 */
public abstract class ApiClient {

    private static final Logger LOGGER = LoggerFactory.getLogger("apiClient");
    public static final MediaType MEDIA_TYPE_JSON = MediaType.get("application/json; charset=utf-8");
    private static final byte[] EMPTY_BYTE_ARRAY = {};

    public interface ReplyConverter<T> {
        T fromJson(Gson gson, String s);
    }

    public interface StringConverter<T> {
        T fromJson(String s);
    }

    abstract protected Gson gson();

    abstract protected String endPointUrl();

    protected LazyVar<HttpUrl> parsedEndPointUrl = new LazyVar<>(() -> HttpUrl.parse(endPointUrl()));

    abstract protected ApiAuthentication authentication();

    abstract protected OkHttpClient client();

    protected HttpUrl.Builder urlBuilder(@Nullable String path) {
        var urlBuilder = parsedEndPointUrl.get().newBuilder();
        if (path != null) {
            var paths = path.split("/");
            for (var p : paths)
                urlBuilder.addPathSegment(p);
        }

        return urlBuilder;
    }

    protected HttpUrl.Builder urlBuilder(@Nullable String path, @Nullable KV... params) {
        var r = urlBuilder(path);
        if (params != null) {
            for (KV kv : params) {
                r.addQueryParameter(kv.key(), kv.value().toString());
            }
        }

        return r;
    }

    protected Request postRequest(@Nullable String path, @NonNull RequestBody body) {
        var builder = new Request.Builder()
                .url(urlBuilder(path).build())
                .post(body);

        return authentication().handle(builder).build();
    }

    public static RequestBody jsonBody(@Nullable String json) {
        if (json != null)
            return RequestBody.create(json, MEDIA_TYPE_JSON);
        else
            return RequestBody.create(EMPTY_BYTE_ARRAY, null);
    }

    public static <T> Reply<T> get(
            @NonNull Response resp,
            @NonNull StringConverter<Reply<T>> converter
    ) {
        var body = resp.body();
        if (body != null) {
            var category = resp.code() / 100;
            if (category != 2)
                throw new ErrorWithCode(
                        Errors.EXECUTION_ERROR,
                        String.format("HTTP status: %d, %s", resp.code(), resp.message())
                );

            try {
                return converter.fromJson(body.string());
            } catch (IOException e) {
                return Reply.error(Errors.IO_ERROR);
            }
        } else
            throw ErrorWithCode.internalError("API Server response empty body: " + resp);
    }

    public static <T> T call(
            @NonNull OkHttpClient client,
            @NonNull Request req,
            @NonNull StringConverter<T> converter) {
        try (final var resp = client.newCall(req).execute()) {
            var body = resp.body();
            if (body != null) {
                var category = resp.code() / 100;
                if (category != 2)
                    throw new ErrorWithCode(
                            Errors.EXECUTION_ERROR,
                            String.format("HTTP status: %d, %s", resp.code(), resp.message())
                    );

                return converter.fromJson(body.string());
            } else
                throw ErrorWithCode.internalError("API Server response empty body: " + resp);
        } catch (ErrorWithCode err) {
            LOGGER.error("Error occurred when call API: " + req.url(), err);
            throw err;
        } catch (Throwable t) {
            var errMsg = "Error occurred when call API: " + req.url();
            LOGGER.error(errMsg, t);
            throw ErrorWithCode.internalError(errMsg);
        }
    }

    public <T> T call(@NonNull Request req, @NonNull ReplyConverter<T> converter) {
        try (final var resp = client().newCall(req).execute()) {
            var body = resp.body();
            if (body != null) {
                var category = resp.code() / 100;
                if (category != 2)
                    throw new ErrorWithCode(
                            Errors.EXECUTION_ERROR,
                            String.format("HTTP status: %d, %s", resp.code(), resp.message())
                    );

                return converter.fromJson(gson(), body.string());
            } else
                throw ErrorWithCode.internalError("API Server response empty body: " + resp);
        } catch (ErrorWithCode err) {
            LOGGER.error("Error occurred when call API: " + req.url(), err);
            throw err;
        } catch (Throwable t) {
            var errMsg = "Error occurred when call API: " + req.url();
            LOGGER.error(errMsg, t);
            throw ErrorWithCode.internalError(errMsg);
        }
    }

    public <T> Reply<T> call(@NonNull Request req, @NonNull Type typeOfReplyElmt) {
        return call(req, (gson, s) -> gson.fromJson(s, typeOfReplyElmt));
    }

    public <T> T call(@NonNull Request req, @NonNull Class<T> clzz) {
        return call(req, (gson, s) -> gson.fromJson(s, clzz));
    }

    public <T> JsonElement callForJson(@NonNull Request req) {
        return call(req, (gson, s) -> JsonParser.parseString(s));
    }

    public KV[] addPaginationParams(@Nullable KV[] params, @Nullable Pagination pagination) {
        if (pagination != null) {
            var paramsLen = params != null ? params.length : 0;

            KV[] r = new KV[paramsLen + 2];
            if (params != null)
                System.arraycopy(params, 0, r, 0, params.length);

            r[paramsLen] = new KV("__limit", pagination.limit);
            r[paramsLen + 1] = new KV("__page", pagination.page);

            return r;
        } else
            return params;
    }

    public <T> Reply<T> get(
            @Nullable String path,
            @NonNull Type typeOfReplyElmt,
            @Nullable ReqHooker reqHooker,
            @Nullable Pagination pagination,
            @Nullable KV... params) {
        params = addPaginationParams(params, pagination);
        HttpUrl url = urlBuilder(path, params).build();

        var builder = new Request.Builder().url(url);
        var req = authentication().handle(builder).build();
        if (reqHooker != null)
            reqHooker.use(req);

        return call(req, typeOfReplyElmt);
    }

    public <T> Reply<T> get(
            @Nullable String path,
            @NonNull Type typeOfReplyElmt,
            @Nullable ReqHooker reqHooker,
            @Nullable KV... params) {
        return get(path, typeOfReplyElmt, reqHooker, null, params);
    }

    public <T> Reply<T> get(
            @Nullable String path,
            @NonNull Type typeOfReplyElmt,
            @Nullable KV... params) {
        return get(path, typeOfReplyElmt, null, null, params);
    }

    public <T> Reply<T> post(
            @Nullable String path,
            @NonNull Type typeOfReplyElmt,
            @Nullable ReqHooker reqHooker,
            @Nullable Object body) {
        var bodyJson = body != null ? (body instanceof String s ? s : gson().toJson(body)) : null;
        var req = postRequest(path, jsonBody(bodyJson));
        if (reqHooker != null) {
            reqHooker.use(req);
            reqHooker.useBody(bodyJson);
        }

        return call(req, typeOfReplyElmt);
    }

    public Reply<Void> postRaw(
            @Nullable String path,
            @Nullable ReqHooker reqHooker,
            @Nullable Object body) {
        return post(path, Reply.RAW_REPLY_TYPE, reqHooker, body);
    }

    public Reply<Void> delete(
            @Nullable String path,
            @Nullable ReqHooker reqHooker,
            @Nullable KV...params
    ) {
        HttpUrl url = urlBuilder(path, params).build();

        var builder = new Request.Builder().url(url);
        var req = authentication().handle(builder).delete().build();
        if (reqHooker != null)
            reqHooker.use(req);

        return call(req, Reply.RAW_REPLY_TYPE);
    }
}
