package com.lucendar.gnss.sdk.strm;

import java.util.StringJoiner;

public class StrmApiCall {
    private String reqId;
    private long reqTm;
    private String apiName;
    private long callTm;
    private String qryParams;
    private String reqBody;
    private String resp;
    private Integer err;

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public long getReqTm() {
        return reqTm;
    }

    public void setReqTm(long reqTm) {
        this.reqTm = reqTm;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public long getCallTm() {
        return callTm;
    }

    public void setCallTm(long callTm) {
        this.callTm = callTm;
    }

    public String getQryParams() {
        return qryParams;
    }

    public void setQryParams(String qryParams) {
        this.qryParams = qryParams;
    }

    public String getReqBody() {
        return reqBody;
    }

    public void setReqBody(String reqBody) {
        this.reqBody = reqBody;
    }

    public String getResp() {
        return resp;
    }

    public void setResp(String resp) {
        this.resp = resp;
    }

    public Integer getErr() {
        return err;
    }

    public void setErr(Integer err) {
        this.err = err;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", StrmApiCall.class.getSimpleName() + "[", "]")
                .add("reqId='" + reqId + "'")
                .add("reqTm=" + reqTm)
                .add("apiName='" + apiName + "'")
                .add("callTm=" + callTm)
                .add("qryParams='" + qryParams + "'")
                .add("reqBody='" + reqBody + "'")
                .add("resp='" + resp + "'")
                .add("err=" + err)
                .toString();
    }
}
