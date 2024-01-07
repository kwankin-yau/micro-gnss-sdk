/*
 * Copyright (c) 2023 lucendar.com.
 * All rights reserved.
 */
package com.lucendar.gnss.sdk.strm;

import com.lucendar.common.utils.DateTimeUtils;
import info.gratour.common.error.ErrorWithCode;

import java.time.format.DateTimeParseException;
import java.util.StringJoiner;

public class QryAvUploadReq {
    private String reqId;
    private long startTimeStart;
    private long startTimeEnd;
    private String simNo;
    private int __limit;
    private int __page;

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public long getStartTimeStart() {
        return startTimeStart;
    }

    public void setStartTimeStart(long startTimeStart) {
        this.startTimeStart = startTimeStart;
    }

    public long getStartTimeEnd() {
        return startTimeEnd;
    }

    public void setStartTimeEnd(long startTimeEnd) {
        this.startTimeEnd = startTimeEnd;
    }

    public String getSimNo() {
        return simNo;
    }

    public void setSimNo(String simNo) {
        this.simNo = simNo;
    }

    public int get__limit() {
        return __limit;
    }

    public void set__limit(int __limit) {
        this.__limit = __limit;
    }

    public int get__page() {
        return __page;
    }

    public void set__page(int __page) {
        this.__page = __page;
    }

    public void setStartTimeStart(String value) {
        if (value.isEmpty())
            throw ErrorWithCode.invalidParam("startTmStart");

        try {
            startTimeStart = DateTimeUtils.BeijingConv.stringToMillis(value);
        } catch (DateTimeParseException e) {
            throw ErrorWithCode.invalidParam("startTmStart");
        }
    }

    public void setStartTimeEnd(String value) {
        if (value.isEmpty())
            throw ErrorWithCode.invalidParam("startTmEnd");

        try {
            startTimeEnd = DateTimeUtils.BeijingConv.stringToMillis(value);
        } catch (DateTimeParseException e) {
            throw ErrorWithCode.invalidParam("startTmEnd");
        }
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", QryAvUploadReq.class.getSimpleName() + "[", "]")
                .add("reqId='" + reqId + "'")
                .add("startTimeStart=" + startTimeStart)
                .add("startTimeEnd=" + startTimeEnd)
                .add("simNo='" + simNo + "'")
                .add("__limit=" + __limit)
                .add("__page=" + __page)
                .toString();
    }
}
