/*******************************************************************************
 *  Copyright (c) 2019, 2022 lucendar.com.
 *  All rights reserved.
 *
 *  Contributors:
 *     KwanKin Yau (alphax@vip.163.com) - initial API and implementation
 *******************************************************************************/
package com.lucendar.gnss.sdk.gateway.spi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lucendar.strm.common.gateway.AvUploadCompleted;
import info.gratour.common.types.rest.Reply;
import info.gratour.jt808common.spi.model.TermCmdStateChanged;
import com.lucendar.gnss.sdk.almatt.AlmAttUploadReq;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class GatewaySpiClient {

    private String urlPrefix;
    private final OkHttpClient client;
    private final Gson gson = new GsonBuilder().create();
    private final MediaType JSON = MediaType.get("application/json");

    private void setUrlPrefix(String urlPrefix) {
        if (urlPrefix.endsWith("/"))
            this.urlPrefix = urlPrefix.substring(0, urlPrefix.length() - 1); // remove ending backslash
        else
            this.urlPrefix = urlPrefix;
    }

    public GatewaySpiClient(String urlPrefix) {
        setUrlPrefix(urlPrefix);

        client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .callTimeout(20, TimeUnit.SECONDS)
                .build();
    }

    public GatewaySpiClient(String urlPrefix, OkHttpClient client) {
        setUrlPrefix(urlPrefix);
        this.client = client;
    }

    protected void callApi(String endPoint, String json) {
        Request req = new Request.Builder()
                .url(urlPrefix + endPoint)
                .post(RequestBody.create(json, JSON))
                .build();

        try (Response resp = this.client.newCall(req).execute()) {
            ResponseBody body = resp.body();
            if (body != null) {
                int httpCategory = resp.code() / 100;
                if (httpCategory != 2) {
                    String message = "API `%s` call return status: %d.";
                    message = String.format(message, endPoint, resp.code());
                    throw new RuntimeException(message);
                }

                String s = body.string();
                Reply<?> r = gson.fromJson(s, Reply.RAW_REPLY_TYPE);
                if (!r.ok()) {
                    String message = "API `%s` call return error: %d, %s.";
                    message = String.format(message, endPoint, r.getErrCode(), r.getMessage());
                    throw new RuntimeException(message);
                }
            } else
                throw new RuntimeException("API Server response empty body: " + resp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void notifCmdStateChanged(TermCmdStateChanged changed) {
        String json = gson.toJson(changed);

        callApi("/spi/cmd/notif", json);
    }

    public void notifAvUploadCompleted(AvUploadCompleted completed) {
        String json = gson.toJson(completed);

        callApi("/spi/av_upload_completed", json);
    }

    public void reqAlmAttUpload(AlmAttUploadReq req) {
        String json = gson.toJson(req);

        callApi("/spi/alm_att_upload", json);
    }

}
