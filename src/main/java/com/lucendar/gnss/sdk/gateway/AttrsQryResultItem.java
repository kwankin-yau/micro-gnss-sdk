package com.lucendar.gnss.sdk.gateway;

import com.lucendar.strm.common.StreamingApi;
import com.lucendar.strm.common.types.ScopedSimNo;
import info.gratour.jt808common.protocol.msg.types.ackparams.JT808AckParams;

import java.util.Objects;
import java.util.StringJoiner;

public class AttrsQryResultItem<ATTRS extends JT808AckParams> {

    private String appId;
    private String simNo;
    private ATTRS attrs;

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

    public void setScopedSimNo(ScopedSimNo scopedSimNo) {
        this.appId = scopedSimNo.getAppId();
        this.simNo = scopedSimNo.getSimNo();
    }

    public ATTRS getAttrs() {
        return attrs;
    }

    public void setAttrs(ATTRS attrs) {
        this.attrs = attrs;
    }

    public String appIdDef() {
        return Objects.requireNonNullElse(appId, StreamingApi.DEFAULT_APP_ID);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AttrsQryResultItem.class.getSimpleName() + "[", "]")
                .add("appId='" + appId + "'")
                .add("simNo='" + simNo + "'")
                .add("attrs=" + attrs)
                .toString();
    }
}
