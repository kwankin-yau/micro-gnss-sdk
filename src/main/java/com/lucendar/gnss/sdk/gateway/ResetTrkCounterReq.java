package com.lucendar.gnss.sdk.gateway;

import java.util.Arrays;
import java.util.StringJoiner;

public class ResetTrkCounterReq {

    private String[] simNos;

    public ResetTrkCounterReq() {
    }

    public ResetTrkCounterReq(String[] simNos) {
        this.simNos = simNos;
    }

    public String[] getSimNos() {
        return simNos;
    }

    public void setSimNos(String[] simNos) {
        this.simNos = simNos;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ResetTrkCounterReq.class.getSimpleName() + "[", "]")
                .add("simNos=" + Arrays.toString(simNos))
                .toString();
    }
}
