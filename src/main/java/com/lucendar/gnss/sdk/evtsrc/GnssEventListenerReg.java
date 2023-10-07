package com.lucendar.gnss.sdk.evtsrc;

import java.util.StringJoiner;

public class GnssEventListenerReg {
    private final GnssEventType eventType;
    private final GnssEventListener listener;

    public GnssEventListenerReg(GnssEventType eventType, GnssEventListener listener) {
        this.eventType = eventType;
        this.listener = listener;
    }

    public GnssEventType getEventType() {
        return eventType;
    }

    public GnssEventListener getListener() {
        return listener;
    }

    public static GnssEventListenerReg strm(GnssEventListener listener) {
        return new GnssEventListenerReg(GnssEventType.strm, listener);
    }

    public static GnssEventListenerReg av_upload(GnssEventListener listener) {
        return new GnssEventListenerReg(GnssEventType.av_upload, listener);
    }

    public static GnssEventListenerReg cmd(GnssEventListener listener) {
        return new GnssEventListenerReg(GnssEventType.cmd, listener);
    }

    public String wsDestOfQueue(String token) {
        return "/user/" + token + "/queue/" + eventType.name();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", GnssEventListenerReg.class.getSimpleName() + "[", "]")
                .add("eventType=" + eventType)
                .add("listener=" + listener)
                .toString();
    }
}
