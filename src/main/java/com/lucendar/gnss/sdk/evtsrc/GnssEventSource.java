package com.lucendar.gnss.sdk.evtsrc;

public interface GnssEventSource {
    /**
     * Immediately and violently release resources held by this event source.
     */
    void cancel();
}
