package com.lucendar.gnss.sdk.evtsrc;

public interface GnssEventListener {

    void afterSubscribed(GnssEventType eventType);

    /**
     *
     * @param eventType
     * @param t
     */
    void onFailure(GnssEventType eventType, Throwable t);

    void onEvent(GnssEvent event);
}
