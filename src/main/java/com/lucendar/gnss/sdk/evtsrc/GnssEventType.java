package com.lucendar.gnss.sdk.evtsrc;

public enum GnssEventType {
    /**
     * heartbeat
     */
    cmd,
    av_upload,
    strm;

    public static GnssEventType tryParse(String eventType) {
        switch (eventType) {
            case "cmd":
                return cmd;

            case "av_upload":
                return av_upload;

            case "strm":
                return strm;

            default:
                return null;
        }
    }
}
