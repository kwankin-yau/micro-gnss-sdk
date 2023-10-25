package com.lucendar.gnss.sdk.strm;

import com.lucendar.strm.common.StreamingApi;
import com.lucendar.strm.common.strm.AudioConfig;
import com.lucendar.strm.common.strm.OpenStrmReq;
import com.lucendar.strm.common.strm.ServerHint;

public interface GnssOpenStrmReq {

    int FORMAT__HTTP_FLV = StreamingApi.STRM_FORMAT__FLV;
    int FORMAT__RTMP = StreamingApi.STRM_FORMAT__RTMP;
    int FORMAT__HLS = StreamingApi.STRM_FORMAT__HLS;
    int FORMAT__RTSP = StreamingApi.STRM_FORMAT__RTSP;

    String SUB_FORMAT__FMP4 = StreamingApi.STRM_SUB_FORMAT__FMP4;
    String SUB_FORMAT__MPEGTS = StreamingApi.STRM_SUB_FORMAT__MPEGTS;

    String getReqId();
    boolean isAsync();
    boolean isLiveReq();
    String getSimNo();
    byte getChannel();
    int getProto();
    String getSubProto();
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

    AudioConfig getInputAudioCfg();
    AudioConfig getAudioCfg();
    OpenStrmReq.RtspSource getRtspSrc();

    String getTimedToken();
}
