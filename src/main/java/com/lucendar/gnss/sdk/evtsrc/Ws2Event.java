package com.lucendar.gnss.sdk.evtsrc;

import java.util.StringJoiner;

public class Ws2Event {

    /**
     * One of GnssEventType.name()
     */
    private String typ;

    /**
     * message body json
     */
    private String json;

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Ws2Event.class.getSimpleName() + "[", "]")
                .add("typ='" + typ + "'")
                .add("json='" + json + "'")
                .toString();
    }
}
