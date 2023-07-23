package com.lucendar.gnss.sdk.strm;

import com.lucendar.strm.common.strm.AudioConfig;
import com.lucendar.strm.common.strm.OpenStrmReq;
import com.lucendar.strm.common.strm.ServerHint;

public interface GnssOpenStrmReq {
    String getReqId();
    boolean isAsync();
    boolean isLiveReq();
    String getSimNo();
    byte getChannel();
    int getProto();
    byte getConnIdx();
//    boolean isDisableAudio();
    ServerHint getStrmServerHint();
//    byte getTermRev();

    Boolean getDetectMediaTyp();

    /**
     * Requested data type of channel, only valid for live request.
     *
     * @return data type of channel
     */
    int liveDataType();
    int replayMediaType();

    default int liveDataTypeOrReplayMediaType() {
        if (isLiveReq())
            return liveDataType();
        else
            return replayMediaType();
    }

    byte getCodeStrm();
    boolean isExclusive();
    boolean isSaveOnServer();

    /**
     * Get keep interval in seconds.
     *
     * @return Keep interval in seconds. null if use default keep interval.
     */
    Integer getKeepInterval();
    Integer getTalkSendProtoVer();

    AudioConfig getAudioCfg();
    OpenStrmReq.RtspSource getRtspSrc();

    String getTimedToken();
}
