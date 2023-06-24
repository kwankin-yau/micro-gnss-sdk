package com.lucendar.gnss.sdk.gateway;

import java.util.StringJoiner;

public class TermCounter {

    private String simNo;
    private long trkCnt;

    public TermCounter() {
    }

    public TermCounter(String simNo, long trkCnt) {
        this.simNo = simNo;
        this.trkCnt = trkCnt;
    }

    public String getSimNo() {
        return simNo;
    }

    public void setSimNo(String simNo) {
        this.simNo = simNo;
    }

    public long getTrkCnt() {
        return trkCnt;
    }

    public void setTrkCnt(long trkCnt) {
        this.trkCnt = trkCnt;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TermCounter.class.getSimpleName() + "[", "]")
                .add("simNo='" + simNo + "'")
                .add("trkCnt=" + trkCnt)
                .toString();
    }
}
