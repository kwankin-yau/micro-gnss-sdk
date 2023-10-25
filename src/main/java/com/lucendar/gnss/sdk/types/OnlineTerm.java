/*******************************************************************************
 *  Copyright (c) 2019, 2022 lucendar.com.
 *  All rights reserved.
 *
 *  Contributors:
 *     KwanKin Yau (alphax@vip.163.com) - initial API and implementation
 *******************************************************************************/
package com.lucendar.gnss.sdk.types;

import java.util.StringJoiner;

public class OnlineTerm {
    private String simNo;
    private Integer protoVer;
    private Boolean acc;
    private Float spd;

    public String getSimNo() {
        return simNo;
    }

    public void setSimNo(String simNo) {
        this.simNo = simNo;
    }

    public Integer getProtoVer() {
        return protoVer;
    }

    public void setProtoVer(Integer protoVer) {
        this.protoVer = protoVer;
    }

    public Boolean getAcc() {
        return acc;
    }

    public void setAcc(Boolean acc) {
        this.acc = acc;
    }

    public Float getSpd() {
        return spd;
    }

    public void setSpd(Float spd) {
        this.spd = spd;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", OnlineTerm.class.getSimpleName() + "[", "]")
                .add("simNo='" + simNo + "'")
                .add("protoVer=" + protoVer)
                .add("acc=" + acc)
                .add("spd=" + spd)
                .toString();
    }
}
