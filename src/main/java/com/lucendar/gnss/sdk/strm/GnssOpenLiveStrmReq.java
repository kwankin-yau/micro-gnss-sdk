package com.lucendar.gnss.sdk.strm;

import com.lucendar.strm.common.StreamingApi;
import com.lucendar.strm.common.strm.AudioConfig;
import com.lucendar.strm.common.strm.OpenStrmReq;
import com.lucendar.strm.common.strm.ServerHint;
import info.gratour.jt808common.protocol.msg.types.cmdparams.CP_9101_LiveAvReq;

import java.util.StringJoiner;
import java.util.stream.Stream;

public class GnssOpenLiveStrmReq implements GnssOpenStrmReq {

    public static final int PROTO__HTTP_FLV = StreamingApi.STRM_FORMAT__FLV;
    public static final int PROTO__RTMP = StreamingApi.STRM_FORMAT__RTMP;
    public static final int PROTO__HLS = StreamingApi.STRM_FORMAT__HLS;
    public static final int PROTO__RTSP = StreamingApi.STRM_FORMAT__RTSP;

    public static final byte DATA_TYPE__AV = CP_9101_LiveAvReq.DATA_TYPE__AV;
    public static final byte DATA_TYPE__VIDEO = CP_9101_LiveAvReq.DATA_TYPE__VIDEO;
    public static final byte DATA_TYPE__TALK = CP_9101_LiveAvReq.DATA_TYPE__TALK;
    public static final byte DATA_TYPE__LISTEN = CP_9101_LiveAvReq.DATA_TYPE__LISTEN;
    public static final byte DATA_TYPE__BROADCAST = CP_9101_LiveAvReq.DATA_TYPE__BROADCAST;
    public static final byte DATA_TYPE__PASS_THOUGH = CP_9101_LiveAvReq.DATA_TYPE__PASS_THOUGH;

    public static final byte CODE_STREAM__PRIMARY = CP_9101_LiveAvReq.CODE_STREAM__PRIMARY;
    public static final byte CODE_STREAM__SUB = CP_9101_LiveAvReq.CODE_STREAM__SUB;

    public static final int TERM_PROTO__TCP = 0;
    public static final int TERM_PROTO__UDP = 1;

    private String reqId;
    private boolean async = true;
    private String simNo;
    private byte channel;
    private Byte dataType;
    private byte codeStream;
    private int proto;
    private byte connIdx;
    private ServerHint strmServerHint;
    private int termProto;
    private boolean exclusive;
    private boolean saveOnServer;
    private Boolean detectMediaTyp;
    private Integer keepInterval;
    private Integer talkSendProtoVer;
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
        return true;
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

    public Byte getDataType() {
        return dataType;
    }

    public void setDataType(Byte dataType) {
        this.dataType = dataType;
    }

    public byte dataTypeDef() {
        if (dataType == null)
            return 0;
        else
            return dataType;
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

    public int getTermProto() {
        return termProto;
    }

    public void setTermProto(int termProto) {
        this.termProto = termProto;
    }

    @Override
    public int liveDataType() {
        return dataType != null ? dataType : 0;
    }

    @Override
    public int replayMediaType() {
        return 0;
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

    public void setKeepInterval(Integer keepInterval) {
        this.keepInterval = keepInterval;
    }

    @Override
    public Integer getTalkSendProtoVer() {
        return talkSendProtoVer;
    }

    public void setTalkSendProtoVer(Integer talkSendProtoVer) {
        this.talkSendProtoVer = talkSendProtoVer;
    }

    @Override
    public OpenStrmReq.RtspSource getRtspSrc() {
        return rtspSrc;
    }

    public void setRtspSrc(OpenStrmReq.RtspSource rtspSrc) {
        this.rtspSrc = rtspSrc;
    }

    @Override
    public AudioConfig getAudioCfg() {
        return audioCfg;
    }

    public void setAudioCfg(AudioConfig audioCfg) {
        this.audioCfg = audioCfg;
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
        return new StringJoiner(", ", GnssOpenLiveStrmReq.class.getSimpleName() + "[", "]")
                .add("reqId='" + reqId + "'")
                .add("async=" + async)
                .add("simNo='" + simNo + "'")
                .add("channel=" + channel)
                .add("dataType=" + dataType)
                .add("codeStream=" + codeStream)
                .add("proto=" + proto)
                .add("connIdx=" + connIdx)
                .add("strmServerHint=" + strmServerHint)
                .add("termProto=" + termProto)
                .add("exclusive=" + exclusive)
                .add("saveOnServer=" + saveOnServer)
                .add("detectMediaTyp=" + detectMediaTyp)
                .add("keepInterval=" + keepInterval)
                .add("talkSendProtoVer=" + talkSendProtoVer)
                .add("audioCfg=" + audioCfg)
                .add("rtspSrc=" + rtspSrc)
                .add("timedToken='" + timedToken + "'")
                .toString();
    }
}
