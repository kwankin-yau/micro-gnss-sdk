package com.lucendar.gnss.sdk.evtsrc;

import com.google.gson.reflect.TypeToken;
import com.lucendar.gnss.sdk.utils.GnssClient;
import com.lucendar.strm.common.StrmMediaNotif;
import com.lucendar.strm.common.strm.stored.AvUploadNotif;
import info.gratour.jt808common.spi.model.TermCmdStateChanged;

import java.lang.reflect.Type;
import java.util.StringJoiner;

public class GnssEvent {

    private GnssEventType eventType;
    private String eventBody;
    private StrmMediaNotif strmMediaNotif;
    private AvUploadNotif avUploadNotif;
    private TermCmdStateChanged cmdStateChanged;
    private static final Type StrmMediaNotifType = new TypeToken<StrmMediaNotif>() {
    }.getType();
    private static final Type AvUploadNotifType = new TypeToken<AvUploadNotif>() {
    }.getType();


    public GnssEventType getEventType() {
        return eventType;
    }

    public void setEventType(GnssEventType eventType) {
        this.eventType = eventType;
    }

    public String getEventBody() {
        return eventBody;
    }

    public void setEventBody(String eventBody) {
        this.eventBody = eventBody;
    }

    public StrmMediaNotif getStrmMediaNotif() {
        return strmMediaNotif;
    }

    public void setStrmMediaNotif(StrmMediaNotif strmMediaNotif) {
        this.strmMediaNotif = strmMediaNotif;
    }

    public AvUploadNotif getAvUploadNotif() {
        return avUploadNotif;
    }

    public void setAvUploadNotif(AvUploadNotif avUploadNotif) {
        this.avUploadNotif = avUploadNotif;
    }

    public TermCmdStateChanged getCmdStateChanged() {
        return cmdStateChanged;
    }

    public void setCmdStateChanged(TermCmdStateChanged cmdStateChanged) {
        this.cmdStateChanged = cmdStateChanged;
    }

    public static GnssEvent of(GnssEventType evtTyp, String eventBody) {
        GnssEvent r = new GnssEvent();
        r.setEventType(evtTyp);

        switch (evtTyp) {
            case cmd:
                r.cmdStateChanged = GnssClient.GSON.fromJson(eventBody, TermCmdStateChanged.TYPE);
                break;
            case av_upload:
                r.avUploadNotif = GnssClient.GSON.fromJson(eventBody, AvUploadNotifType);
                break;
            case strm:
                r.strmMediaNotif = GnssClient.GSON.fromJson(eventBody, StrmMediaNotifType);
                break;

            default:
                throw new RuntimeException("Unhandled event type: " + evtTyp);
        }

        return r;
    }

    public static GnssEvent of(String eventType, String eventBody) {
        GnssEventType evtTyp = GnssEventType.valueOf(eventType);
        return of(evtTyp, eventBody);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", GnssEvent.class.getSimpleName() + "[", "]")
                .add("eventType=" + eventType)
                .add("strmMediaNotif=" + strmMediaNotif)
                .add("avUploadNotif=" + avUploadNotif)
                .add("cmdStateChanged=" + cmdStateChanged)
                .toString();
    }
}
