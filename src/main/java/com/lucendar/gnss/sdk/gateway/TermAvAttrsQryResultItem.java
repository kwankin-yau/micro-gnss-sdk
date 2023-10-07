package com.lucendar.gnss.sdk.gateway;

import info.gratour.jt808common.protocol.msg.types.JT1078TermAvAttrs;

import java.util.StringJoiner;

public class TermAvAttrsQryResultItem {

    private String simNo;
    private JT1078TermAvAttrs attrs;

    public String getSimNo() {
        return simNo;
    }

    public void setSimNo(String simNo) {
        this.simNo = simNo;
    }

    public JT1078TermAvAttrs getAttrs() {
        return attrs;
    }

    public void setAttrs(JT1078TermAvAttrs attrs) {
        this.attrs = attrs;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TermAvAttrsQryResultItem.class.getSimpleName() + "[", "]")
                .add("simNo='" + simNo + "'")
                .add("attrs=" + attrs)
                .toString();
    }
}
