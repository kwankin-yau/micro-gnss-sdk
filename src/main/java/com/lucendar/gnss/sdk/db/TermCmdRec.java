package com.lucendar.gnss.sdk.db;

import com.lucendar.common.utils.DateTimeUtils;
import info.gratour.jt808common.protocol.msg.types.TermCmdParamsParser;
import info.gratour.jt808common.spi.model.TermCmd;

import java.time.OffsetDateTime;

public class TermCmdRec extends TermCmd {

    private OffsetDateTime stgReqTm;
    private OffsetDateTime stgSendTm;
    private OffsetDateTime stgAckTm;
    private OffsetDateTime stgEndTm;
    private String stgParams;
    private String stgAckParams;

    public OffsetDateTime getStgReqTm() {
        return stgReqTm;
    }

    public void setStgReqTm(OffsetDateTime stgReqTm) {
        this.stgReqTm = stgReqTm;
    }

    public OffsetDateTime getStgSendTm() {
        return stgSendTm;
    }

    public void setStgSendTm(OffsetDateTime stgSendTm) {
        this.stgSendTm = stgSendTm;
    }

    public OffsetDateTime getStgAckTm() {
        return stgAckTm;
    }

    public void setStgAckTm(OffsetDateTime stgAckTm) {
        this.stgAckTm = stgAckTm;
    }

    public OffsetDateTime getStgEndTm() {
        return stgEndTm;
    }

    public void setStgEndTm(OffsetDateTime stgEndTm) {
        this.stgEndTm = stgEndTm;
    }

    public String getStgParams() {
        return stgParams;
    }

    public void setStgParams(String stgParams) {
        this.stgParams = stgParams;
    }

    public String getStgAckParams() {
        return stgAckParams;
    }

    public void setStgAckParams(String stgAckParams) {
        this.stgAckParams = stgAckParams;
    }

    public void fromTermCmd(TermCmd termCmd) {
        this.assignFrom(termCmd);

        stgReqTm = DateTimeUtils.BeijingConv.millisToOdt(getReqTm());
        stgSendTm = DateTimeUtils.BeijingConv.millisToOdtBoxed(getSentTm());
        stgAckTm = DateTimeUtils.BeijingConv.millisToOdtBoxed(getAckTm());
        stgEndTm = DateTimeUtils.BeijingConv.millisToOdtBoxed(getEndTm());
        stgParams = paramsJson();
        stgAckParams = ackParamsJson();
    }

    public TermCmd toTermCmd(TermCmdParamsParser parser) {
        setReqTm(stgReqTm.toInstant().toEpochMilli());
        if (stgSendTm != null)
            setSentTm(stgSendTm.toInstant().toEpochMilli());
        else
            setSentTm((Long) null);
        if (stgAckTm != null)
            setAckTm(stgAckTm.toInstant().toEpochMilli());
        else
            setAckTm((Long) null);
        if (stgEndTm != null)
            setEndTm(stgEndTm.toInstant().toEpochMilli());
        else
            setEndTm((Long) null);

        if (stgParams != null) {
            setParams(parser.cmdParamsFromJson(getMsgId(), stgParams));
        } else
            setParams(null);

        if (stgAckParams != null)
            setAckParams(parser.ackParamsFromJson(getMsgId(), stgAckParams));
        else
            setAckParams(null);

        TermCmd r = new TermCmd();
        r.assignFrom(this);
        return r;
    }
}
