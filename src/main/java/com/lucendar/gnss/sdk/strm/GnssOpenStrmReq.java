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

    /**
     * DISABLED mode. DO NOT trace
     */
    int TRACE_MODE__DISABLED = 0;

    /**
     * SIMPLE mode. Trace:
     * - stream state changes (StrmReq.state)
     * - all terminal inbound/outbound message, including
     */
    int TRACE_MODE__SIMPLE = 1;

    /**
     * ADVANCED mode. Trace:
     * - stream state changes (StrmReq.state)
     * - stream media notification (StrmMediaNotif)
     * - all terminal inbound/outbound message, including
     */
    int TRACE_MODE__ADVANCED = 2;

    /**
     * FULL mode. Trace:
     * - stream state changes(StrmReq.state)
     * - stream media notification (StrmMediaNotif)
     * - stream data
     * - all terminal inbound/outbound message, including
     */
    int TRACE_MODE__FULL = 3;


    String getReqId();
    boolean isAsync();
    boolean isLiveReq();
    String getAppId();
    default String appIdDef() {
        String r = getAppId();
        if (r == null)
            return StreamingApi.DEFAULT_APP_ID;
        else
            return r;
    }

    String getSimNo();
    byte getChannel();

    /**
     * 设置通道号。主要用于对讲时。对讲时可指定 channel = 0。此时媒体代理服务将确定实际的通道号，确定后，通过此方法设置确定后的通道号。
     *
     * @param channel
     */
    void setChannel(byte channel);
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

    /**
     * The trace mode should be applied to this request.
     * Optional. `null` if not specified. Value domain: TRACE_MODE__XXXX constant defined in
     * `com.lucendar.strm.common.strm.OpenStrmReq`.
     *
     * @return trace mode
     */
    Integer getTrace();

    default int traceDef() {
        Integer v = getTrace();
        if (v != null)
            return v;
        else
            return OpenStrmReq.TRACE_MODE__DISABLED;
    }

    default boolean isTraced() {
        Integer trace = getTrace();
        return trace != null && trace != OpenStrmReq.TRACE_MODE__DISABLED;
    }

    /**
     * <pre>
     * The scheme of media playing url. When the `proto` is `FLV` or `HLS`, the scheme of returned url in GNSS
     * `strm/live/open`, `strm/replay/open` API is determined by the value of the `async` parameter:
     * - when `async` is true, return URL with `wss` scheme.
     * - when `async` is false, return URL with `https` scheme.
     *
     * If this logic is not appropriate, application can use this parameter to specify the scheme needed. At present,
     * the following value(case-sensitive) are meaningful:
     * - `http`: support FLV, HLS proto
     * - `https`: support FLV, HLS proto
     * - `ws`: support FLV, HLS proto
     * - `wss`: support FLV, HLS proto
     *
     * This is a optional parameter, if supplied, the `scheme` must be supported by the `proto`.
     *</pre>
     * @return
     */
    String getScheme();

    /**
     * Whether prohibit the server send a stream close command to terminal if an interruption in the stream is detected.
     *
     * @return null let server determine what to do when the code stream is interrupted.
     */
    Boolean getDontSendCloseIfInterrupt();

    default boolean dontSendCloseIfInterruptDef() {
        Boolean v = getDontSendCloseIfInterrupt();
        if (v != null)
            return v;
        else
            return false;
    }

}
