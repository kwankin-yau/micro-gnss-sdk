package com.lucendar.gnss.sdk.gateway;

import com.lucendar.strm.common.StreamingApi;
import com.lucendar.strm.common.types.ScopedSimNo;

import java.util.StringJoiner;

/**
 * 终端上下线通知
 */
public class OnlineOfflineNotif {
    private String appId;
    private String simNo;
    private boolean online;
    private long tm;
    private String gwInstId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSimNo() {
        return simNo;
    }

    public void setSimNo(String simNo) {
        this.simNo = simNo;
    }

    /**
     * 是否上线通知。false表下线通知。
     *
     * @return
     */
    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public long getTm() {
        return tm;
    }

    public void setTm(long tm) {
        this.tm = tm;
    }

    /**
     * 产生本通知的网关的ID。可能为 null。
     *
     * @return
     */
    public String getGwInstId() {
        return gwInstId;
    }

    public void setGwInstId(String gwInstId) {
        this.gwInstId = gwInstId;
    }

    public boolean isOffline() {
        return !online;
    }

    public String appIdDef() {
        if (appId != null)
            return appId;
        else
            return StreamingApi.DEFAULT_APP_ID;
    }

    public ScopedSimNo scopedSimNo() {
        return new ScopedSimNo(appIdDef(), simNo);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", OnlineOfflineNotif.class.getSimpleName() + "[", "]")
                .add("appId='" + appId + "'")
                .add("simNo='" + simNo + "'")
                .add("online=" + online)
                .add("tm=" + tm)
                .add("gwInstId='" + gwInstId + "'")
                .toString();
    }
}
