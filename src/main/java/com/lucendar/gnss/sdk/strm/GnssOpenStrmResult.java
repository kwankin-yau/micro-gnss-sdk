/** *****************************************************************************
 * Copyright (c) 2019, 2021 lucendar.com.
 * All rights reserved.
 *
 * Contributors:
 * KwanKin Yau (alphax@vip.163.com) - initial API and implementation
 * ***************************************************************************** */
package com.lucendar.gnss.sdk.strm;

import com.google.gson.reflect.TypeToken;
import info.gratour.common.types.rest.Reply;

import java.lang.reflect.Type;
import java.util.StringJoiner;

public class GnssOpenStrmResult {

    public static final Type REPLY_TYPE = new TypeToken<Reply<GnssOpenStrmResult>>(){}.getType();

    private String reqId;
    private String instanceId;
    private Boolean ctrl;
    private boolean ready;
    private String playUrl;

    /**
     * WebSocket地址，用于对讲时客户端提交音频数据。此地址使用stomp协议。仅当请求的流类型为实时流，且数据类型(`dataTyp`)为`2: 对讲`时有效。
     */
    private String wsUrl;

    /**
     * WebSocket地址，用于对讲时客户端提交音频数据。此地址使用二进制websocket协议。仅当请求的流类型为实时流，且数据类型(`dataTyp`)为`2: 对讲`时有效。
     */
    private String taUrl;
    private String mediaTyp;

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public Boolean getCtrl() {
        return ctrl;
    }

    public void setCtrl(Boolean ctrl) {
        this.ctrl = ctrl;
    }

    public boolean ctrlDef() {
        if (ctrl != null)
            return ctrl;
        else
            return false;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public String getWsUrl() {
        return wsUrl;
    }

    public void setWsUrl(String wsUrl) {
        this.wsUrl = wsUrl;
    }

    public String getTaUrl() {
        return taUrl;
    }

    public void setTaUrl(String taUrl) {
        this.taUrl = taUrl;
    }

    public String getMediaTyp() {
        return mediaTyp;
    }

    public void setMediaTyp(String mediaTyp) {
        this.mediaTyp = mediaTyp;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", GnssOpenStrmResult.class.getSimpleName() + "[", "]")
                .add("reqId='" + reqId + "'")
                .add("instanceId='" + instanceId + "'")
                .add("ctrl=" + ctrl)
                .add("ready=" + ready)
                .add("playUrl='" + playUrl + "'")
                .add("wsUrl='" + wsUrl + "'")
                .add("taUrl='" + taUrl + "'")
                .add("mediaTyp='" + mediaTyp + "'")
                .toString();
    }
}
