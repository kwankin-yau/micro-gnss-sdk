package com.lucendar.gnss.sdk.evtsrc;

public interface GnssWs extends GnssEventSource{

    int DEFAULT_CONNECT_TIMEOUT = 6;
    int DEFAULT_RECONNECT_INTERVAL = 10;

    boolean isConnected();

    boolean isActive();

    void open();

    void close();


}

