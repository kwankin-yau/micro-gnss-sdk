/*******************************************************************************
 *  Copyright (c) 2019, 2021 lucendar.com.
 *  All rights reserved.
 *
 *  Contributors:
 *     KwanKin Yau (alphax@vip.163.com) - initial API and implementation
 *******************************************************************************/
package com.lucendar.gnss.sdk.types;

import java.util.Arrays;
import java.util.StringJoiner;

public class AvUploadSetMonitoringReq {

    // This field is marked as `transient` which used by `strm-proxy` internal, client code need not set this field.
    private transient String token;
    private String[] reqIds;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String[] getReqIds() {
        return reqIds;
    }

    public void setReqIds(String[] reqIds) {
        this.reqIds = reqIds;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AvUploadSetMonitoringReq.class.getSimpleName() + "[", "]")
                .add("token='" + token + "'")
                .add("reqIds=" + Arrays.toString(reqIds))
                .toString();
    }
}
