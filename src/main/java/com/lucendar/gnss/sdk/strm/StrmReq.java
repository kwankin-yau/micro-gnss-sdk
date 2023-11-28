package com.lucendar.gnss.sdk.strm;

import java.util.StringJoiner;

/**
 * A database representation of GnssOpenStrmReq.
 */
public class StrmReq implements CompoundStrmReqId {

    public static final int TYP__LIVE = 1;
    public static final int TYP__REPLAY = 2;

    public static final int TRACE_MODE__DISABLED = GnssOpenStrmReq.TRACE_MODE__DISABLED;
    public static final int TRACE_MODE__SIMPLE = GnssOpenStrmReq.TRACE_MODE__SIMPLE;
    public static final int TRACE_MODE__ADVANCED = GnssOpenStrmReq.TRACE_MODE__ADVANCED;
    public static final int TRACE_MODE__FULL = GnssOpenStrmReq.TRACE_MODE__FULL;

    public static final int STATE__NOT_TRACED = -1;
    public static final int STATE__INIT = 0;
    public static final int STATE__MEDIA_SERVER_API_CALLED = 1;
    public static final int STATE__OPEN_STRM_CMD_SENT = 2;
    public static final int STATE__RETRY_DUE_TO_OPEN_STRM_CMD_FAILED = 3;
    public static final int STATE__STREAM_READY = 4;
    public static final int STATE__INVALID_REQUEST = 5;
    public static final int STATE__CLOSED_DUE_TO_STRM_CMD_FAILED = 20;
    public static final int STATE__CLOSED_DUE_TO_CLIENT_REQUEST = 21;
    public static final int STATE__MEDIA_SERVER_API_CALL_FAILED = 22;
    public static final int STATE__BAD_MEDIA_FORMAT = 23;
    public static final int STATE__STRM_ABORTED = 24;
    public static final int STATE__MEDIA_DATA_TIMEOUT = 25;
    public static final int STATE__CLIENT_KEEP_TIMEOUT = 26;

    /**
     * The whole request timed out.
     */
    public static final int STATE__REQUEST_TIMEOUT = 27;
    public static final int STATE__INTERNAL_ERROR = 28;

    /**
     * The request is no longer valid due to some unknown reason.
     */
    public static final int STATE__REQUEST_INVALID = 29;

    public static boolean isFinalState(int state) {
        return state == STATE__NOT_TRACED || state >= STATE__CLOSED_DUE_TO_STRM_CMD_FAILED;
    }


    private String reqId;
    private int typ;
    private long reqTm;
    private String simNo;
    private Integer chan;
    private Integer mediaTyp;
    private int fmt;
    private String subFmt;
    private Boolean detectMediaTyp;
    private Integer codeStrm;
    private Boolean exclusive;
    private Boolean saveOnServer;
    private String reqBody;

    /**
     * Trace mode ID. Value domain: one of `TRACE_MODE__xxx` constant.
     */
    private int trace;
    /**
     * The instance ID of the media server.
     */
    private String strmInst;
    private int state;
    private String errMsg;
    private java.lang.Long closeTm;

    @Override
    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public int getTyp() {
        return typ;
    }

    public void setTyp(int typ) {
        this.typ = typ;
    }

    @Override
    public long getReqTm() {
        return reqTm;
    }

    public void setReqTm(long reqTm) {
        this.reqTm = reqTm;
    }

    public String getSimNo() {
        return simNo;
    }

    public void setSimNo(String simNo) {
        this.simNo = simNo;
    }

    public Integer getChan() {
        return chan;
    }

    public void setChan(Integer chan) {
        this.chan = chan;
    }

    public Integer getMediaTyp() {
        return mediaTyp;
    }

    public void setMediaTyp(Integer mediaTyp) {
        this.mediaTyp = mediaTyp;
    }

    public void setMediaType(Byte mediaType) {
        if (mediaType != null)
            this.mediaTyp = mediaTyp.intValue();
        else
            this.mediaTyp = null;
    }

    public int getFmt() {
        return fmt;
    }

    public void setFmt(int fmt) {
        this.fmt = fmt;
    }

    public String getSubFmt() {
        return subFmt;
    }

    public void setSubFmt(String subFmt) {
        this.subFmt = subFmt;
    }

    public Boolean getDetectMediaTyp() {
        return detectMediaTyp;
    }

    public void setDetectMediaTyp(Boolean detectMediaTyp) {
        this.detectMediaTyp = detectMediaTyp;
    }

    public Integer getCodeStrm() {
        return codeStrm;
    }

    public void setCodeStrm(Integer codeStrm) {
        this.codeStrm = codeStrm;
    }

    public Boolean getExclusive() {
        return exclusive;
    }

    public void setExclusive(Boolean exclusive) {
        this.exclusive = exclusive;
    }

    public Boolean getSaveOnServer() {
        return saveOnServer;
    }

    public void setSaveOnServer(Boolean saveOnServer) {
        this.saveOnServer = saveOnServer;
    }

    public String getReqBody() {
        return reqBody;
    }

    public void setReqBody(String reqBody) {
        this.reqBody = reqBody;
    }

    public int getTrace() {
        return trace;
    }

    public void setTrace(int trace) {
        this.trace = trace;
    }

    public String getStrmInst() {
        return strmInst;
    }

    public void setStrmInst(String strmInst) {
        this.strmInst = strmInst;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public java.lang.Long getCloseTm() {
        return closeTm;
    }

    public void setCloseTm(java.lang.Long closeTm) {
        this.closeTm = closeTm;
    }

    public boolean finalState() {
        return isFinalState(state);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", StrmReq.class.getSimpleName() + "[", "]")
                .add("reqId='" + reqId + "'")
                .add("typ=" + typ)
                .add("reqTm=" + reqTm)
                .add("simNo='" + simNo + "'")
                .add("chan=" + chan)
                .add("mediaTyp=" + mediaTyp)
                .add("fmt=" + fmt)
                .add("subFmt='" + subFmt + "'")
                .add("detectMediaTyp=" + detectMediaTyp)
                .add("codeStrm=" + codeStrm)
                .add("exclusive=" + exclusive)
                .add("saveOnServer=" + saveOnServer)
                .add("reqBody='" + reqBody + "'")
                .add("trace=" + trace)
                .add("strmInst='" + strmInst + "'")
                .add("state=" + state)
                .add("errMsg='" + errMsg + "'")
                .add("closeTm=" + closeTm)
                .toString();
    }
}
