package com.lucendar.gnss.sdk.evtsrc;

public interface GnssEventListener {

    void afterSubscribed(GnssEventType eventType);

    /**
     *
     * @param eventType
     * @param t
     * @return true if reconnect needed
     */
    void onFailure(GnssEventType eventType, Throwable t);

    void onEvent(GnssEvent event);
}
