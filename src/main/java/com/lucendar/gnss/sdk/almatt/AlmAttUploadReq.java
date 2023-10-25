/*******************************************************************************
 *  Copyright (c) 2019, 2022 lucendar.com.
 *  All rights reserved.
 *
 *  Contributors:
 *     KwanKin Yau (alphax@vip.163.com) - initial API and implementation
 *******************************************************************************/
package com.lucendar.gnss.sdk.almatt;

import java.util.StringJoiner;

public class AlmAttUploadReq {

    private String simNo;
    private String almNo;
    private String almTyp;
    private Integer almLvl;

    private transient Long tm1Millis;

    public String getSimNo() {
        return simNo;
    }

    public void setSimNo(String simNo) {
        this.simNo = simNo;
    }

    public String getAlmNo() {
        return almNo;
    }

    public void setAlmNo(String almNo) {
        this.almNo = almNo;
    }

    public String getAlmTyp() {
        return almTyp;
    }

    public void setAlmTyp(String almTyp) {
        this.almTyp = almTyp;
    }

    public Integer getAlmLvl() {
        return almLvl;
    }

    public void setAlmLvl(Integer almLvl) {
        this.almLvl = almLvl;
    }

    public Long getTm1Millis() {
        return tm1Millis;
    }

    public void setTm1Millis(Long tm1Millis) {
        this.tm1Millis = tm1Millis;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AlmAttUploadReq.class.getSimpleName() + "[", "]")
                .add("simNo='" + simNo + "'")
                .add("almNo='" + almNo + "'")
                .add("almTyp='" + almTyp + "'")
                .add("almLvl=" + almLvl)
                .toString();
    }
}
