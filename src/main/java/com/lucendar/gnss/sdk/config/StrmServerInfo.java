/*******************************************************************************
 *  Copyright (c) 2019, 2021 lucendar.com.
 *  All rights reserved.
 *
 *  Contributors:
 *     KwanKin Yau (alphax@vip.163.com) - initial API and implementation
 *******************************************************************************/
package com.lucendar.gnss.sdk.config;

import com.lucendar.strm.common.info.QryInfoResult;

import java.util.StringJoiner;

public class StrmServerInfo implements Cloneable {

    private StrmMediaConfig config;
    private volatile QryInfoResult info;

    public StrmMediaConfig getConfig() {
        return config;
    }

    public void setConfig(StrmMediaConfig config) {
        this.config = config;
    }

    public QryInfoResult getInfo() {
        return info;
    }

    public void setInfo(QryInfoResult info) {
        this.info = info;
    }

    public void assignFrom(StrmServerInfo source) {
        if (source.config != null)
            this.config = source.config.clone();
        else
            this.config = null;

        if (source.info != null)
            this.info = source.info.clone();
        else
            this.info = null;
    }

    @Override
    public StrmServerInfo clone() {
        StrmServerInfo r = new StrmServerInfo();
        r.assignFrom(this);
        return r;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", StrmServerInfo.class.getSimpleName() + "[", "]")
                .add("config=" + config)
                .add("info=" + info)
                .toString();
    }
}
