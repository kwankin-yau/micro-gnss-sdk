package com.lucendar.gnss.sdk.strm;

import com.lucendar.strm.common.strm.AudioConfig;
import com.lucendar.strm.common.strm.OpenStrmReq;
import com.lucendar.strm.common.strm.ServerHint;
import info.gratour.jt808common.protocol.msg.types.cmdparams.CP_9201_ReplayAvReq;
import info.gratour.jtcommon.BeijingTime;

import java.util.StringJoiner;

public class GnssOpenReplayStrmReq implements GnssOpenStrmReq {

//    public static final int PROTO__HTTP_FLV = StreamingApi.PROTO__HTTP_FLV;
//    public static final int PROTO__RTMP = StreamingApi.PROTO__RTMP;
//    public static final int PROTO__HLS = StreamingApi.PROTO__HLS;

    public static final int MEDIA_TYPE__AV = CP_9201_ReplayAvReq.MEDIA_TYPE__AV;
    public static final int MEDIA_TYPE__AUDIO = CP_9201_ReplayAvReq.MEDIA_TYPE__AUDIO;
    public static final int MEDIA_TYPE__VIDEO = CP_9201_ReplayAvReq.MEDIA_TYPE__VIDEO;
    public static final int MEDIA_TYPE__A_OR_V = CP_9201_ReplayAvReq.MEDIA_TYPE__A_OR_V;

    public static final byte CODE_STREAM__ALL = CP_9201_ReplayAvReq.CODE_STREAM__ALL;
    public static final byte CODE_STREAM__PRIMARY = CP_9201_ReplayAvReq.CODE_STREAM__PRIMARY;
    public static final byte CODE_STREAM__SUB = CP_9201_ReplayAvReq.CODE_STREAM__SUB;

    public static final byte STORAGE_TYPE__ALL = CP_9201_ReplayAvReq.STORAGE_TYPE__ALL;
    public static final byte STORAGE_TYPE__PRIMARY = CP_9201_ReplayAvReq.STORAGE_TYPE__PRIMARY;
    public static final byte STORAGE_TYPE__SECONDARY = CP_9201_ReplayAvReq.STORAGE_TYPE__SECONDARY;

    public static final byte MODE__NORMAL = CP_9201_ReplayAvReq.MODE__NORMAL;
    public static final byte MODE__FAST_FORWARD = CP_9201_ReplayAvReq.MODE__FAST_FORWARD;
    public static final byte MODE__KEY_FRAME_BACKWARD = CP_9201_ReplayAvReq.MODE__KEY_FRAME_BACKWARD;
    public static final byte MODE__KEY_FRAME_PLAY = CP_9201_ReplayAvReq.MODE__KEY_FRAME_PLAY;
    public static final byte MODE__UPLOAD_SINGLE_FRAME = CP_9201_ReplayAvReq.MODE__UPLOAD_SINGLE_FRAME;

    private String reqId;
    private boolean async = true;
    private String simNo;
    private byte channel;
    private byte mediaType;
    private byte codeStream;
    private byte storageType;
    private byte mode;
    private byte factor;
    private String startTime;
    private String endTime;
    private int proto;
    private byte connIdx;
    private ServerHint strmServerHint;
    private boolean exclusive;
    private boolean saveOnServer;
    private Boolean detectMediaTyp;
    private Integer keepInterval;
    private AudioConfig audioCfg;
    private OpenStrmReq.RtspSource rtspSrc;
    private String timedToken;


    @Override
    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    @Override
    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    @Override
    public boolean isLiveReq() {
        return false;
    }

    @Override
    public String getSimNo() {
        return simNo;
    }

    public void setSimNo(String simNo) {
        this.simNo = simNo;
    }

    @Override
    public byte getChannel() {
        return channel;
    }

    public void setChannel(byte channel) {
        this.channel = channel;
    }

    public byte getMediaType() {
        return mediaType;
    }

    public void setMediaType(byte mediaType) {
        this.mediaType = mediaType;
    }

    public byte getCodeStream() {
        return codeStream;
    }

    public void setCodeStream(byte codeStream) {
        this.codeStream = codeStream;
    }

    @Override
    public byte getCodeStrm() {
        return codeStream;
    }

    public byte getStorageType() {
        return storageType;
    }

    public void setStorageType(byte storageType) {
        this.storageType = storageType;
    }

    public byte getMode() {
        return mode;
    }

    public void setMode(byte mode) {
        this.mode = mode;
    }

    public byte getFactor() {
        return factor;
    }

    public void setFactor(byte factor) {
        this.factor = factor;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Long startTimeEpoch() {
        if (startTime != null)
            return BeijingTime.stringToMillis(startTime);
        else
            return null;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Long endTimeEpoch() {
        if (endTime != null && !endTime.equals("00-00-00 00:00:00"))
            return BeijingTime.stringToMillis(endTime);
        else
            return null;
    }

    @Override
    public int getProto() {
        return proto;
    }

    public void setProto(int proto) {
        this.proto = proto;
    }

    @Override
    public byte getConnIdx() {
        return connIdx;
    }

    public void setConnIdx(byte connIdx) {
        this.connIdx = connIdx;
    }

    @Override
    public ServerHint getStrmServerHint() {
        return strmServerHint;
    }

    public void setStrmServerHint(ServerHint strmServerHint) {
        this.strmServerHint = strmServerHint;
    }

    @Override
    public int liveDataType() {
        return 0;
    }

    @Override
    public int replayMediaType() {
        return mediaType;
    }

    @Override
    public boolean isExclusive() {
        return exclusive;
    }

    public void setExclusive(boolean exclusive) {
        this.exclusive = exclusive;
    }

    @Override
    public boolean isSaveOnServer() {
        return saveOnServer;
    }

    public void setSaveOnServer(boolean saveOnServer) {
        this.saveOnServer = saveOnServer;
    }

    @Override
    public Boolean getDetectMediaTyp() {
        return detectMediaTyp;
    }

    public void setDetectMediaTyp(Boolean detectMediaTyp) {
        this.detectMediaTyp = detectMediaTyp;
    }

    @Override
    public Integer getKeepInterval() {
        return keepInterval;
    }

    @Override
    public Integer getTalkSendProtoVer() {
        return null;
    }

    @Override
    public AudioConfig getAudioCfg() {
        return audioCfg;
    }

    public void setAudioCfg(AudioConfig audioCfg) {
        this.audioCfg = audioCfg;
    }

    @Override
    public OpenStrmReq.RtspSource getRtspSrc() {
        return rtspSrc;
    }

    public void setKeepInterval(Integer keepInterval) {
        this.keepInterval = keepInterval;
    }

    public void setRtspSrc(OpenStrmReq.RtspSource rtspSrc) {
        this.rtspSrc = rtspSrc;
    }

    @Override
    public String getTimedToken() {
        return timedToken;
    }

    public void setTimedToken(String timedToken) {
        this.timedToken = timedToken;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", GnssOpenReplayStrmReq.class.getSimpleName() + "[", "]")
                .add("reqId='" + reqId + "'")
                .add("async=" + async)
                .add("simNo='" + simNo + "'")
                .add("channel=" + channel)
                .add("mediaType=" + mediaType)
                .add("codeStream=" + codeStream)
                .add("storageType=" + storageType)
                .add("mode=" + mode)
                .add("factor=" + factor)
                .add("startTime='" + startTime + "'")
                .add("endTime='" + endTime + "'")
                .add("proto=" + proto)
                .add("connIdx=" + connIdx)
                .add("strmServerHint=" + strmServerHint)
                .add("exclusive=" + exclusive)
                .add("saveOnServer=" + saveOnServer)
                .add("detectMediaTyp=" + detectMediaTyp)
                .add("keepInterval=" + keepInterval)
                .add("audioCfg=" + audioCfg)
                .add("rtspSrc=" + rtspSrc)
                .add("timedToken='" + timedToken + "'")
                .toString();
    }
}
