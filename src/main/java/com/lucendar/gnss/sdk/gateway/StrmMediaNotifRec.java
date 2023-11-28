package com.lucendar.gnss.sdk.gateway;

import com.lucendar.strm.common.StrmMediaNotif;

import java.util.StringJoiner;

public class StrmMediaNotifRec {

    private String reqId;
    private long reqTm;
    private long notifTm;
    private StrmMediaNotif notif;

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public long getReqTm() {
        return reqTm;
    }

    public void setReqTm(long reqTm) {
        this.reqTm = reqTm;
    }

    public long getNotifTm() {
        return notifTm;
    }

    public void setNotifTm(long notifTm) {
        this.notifTm = notifTm;
    }

    public StrmMediaNotif getNotif() {
        return notif;
    }

    public void setNotif(StrmMediaNotif notif) {
        this.notif = notif;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", StrmMediaNotifRec.class.getSimpleName() + "[", "]")
                .add("reqId='" + reqId + "'")
                .add("reqTm=" + reqTm)
                .add("notifTm=" + notifTm)
                .add("notif=" + notif)
                .toString();
    }
}
