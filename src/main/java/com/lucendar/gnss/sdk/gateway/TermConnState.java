/*******************************************************************************
 *  Copyright (c) 2019, 2020 lucendar.com.
 *  All rights reserved.
 *
 *  Contributors:
 *     KwanKin Yau (alphax@vip.163.com) - initial API and implementation
 *******************************************************************************/
package com.lucendar.gnss.sdk.gateway;

import info.gratour.jt808common.protocol.msg.types.trk.Trk;

import java.util.StringJoiner;

public class TermConnState {

    /**
     * 终端识别号
     */
    private String simNo;

    /**
     * 连接次数
     */
    private long connTimes;

    /**
     * 最后连接时间, epoch millis
     */
    private long connAt;

    /**
     * 终端的协议版本：
     * - 0: 2013
     * - 1: 2019
     * - 255: 未知
     */
    private byte protoVer;

    /**
     * 最后上传或应答时间，epoch millis
     */
    private long lastActive;

    /**
     * 连接期间上报的轨迹数
     */
    private long trkCnt;

    /**
     * ACC 状态
     */
    private Boolean acc;

    /**
     * GPS速度
     */
    private Float spd;

    /**
     * 视频报警状态字
     */
    private Integer vidAlm;

    /**
     * 轨迹对象
     */
    private Trk latestTrk;

    public String getSimNo() {
        return simNo;
    }

    public void setSimNo(String simNo) {
        this.simNo = simNo;
    }

    /**
     * The times of terminal connected to server .
     *
     * @return
     */
    public long getConnTimes() {
        return connTimes;
    }

    public void setConnTimes(long connTimes) {
        this.connTimes = connTimes;
    }

    public long getConnAt() {
        return connAt;
    }

    public void setConnAt(long connAt) {
        this.connAt = connAt;
    }

    public Integer getVidAlm() {
        return vidAlm;
    }

    public void setVidAlm(Integer vidAlm) {
        this.vidAlm = vidAlm;
    }

    public byte getProtoVer() {
        return protoVer;
    }

    public void setProtoVer(byte protoVer) {
        this.protoVer = protoVer;
    }

    public long getLastActive() {
        return lastActive;
    }

    public void setLastActive(long lastActive) {
        this.lastActive = lastActive;
    }

    public long getTrkCnt() {
        return trkCnt;
    }

    public void setTrkCnt(long trkCnt) {
        this.trkCnt = trkCnt;
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

    /**
     * The latest track is received in this connection.
     *
     * @return latest track is received in this connection.
     */
    public Trk getLatestTrk() {
        return latestTrk;
    }

    public void setLatestTrk(Trk latestTrk) {
        this.latestTrk = latestTrk;
        if (latestTrk != null) {
            this.acc = latestTrk.stTestAccOn();
            this.spd = latestTrk.getSpd();
            this.vidAlm = latestTrk.getVidAlm();
        }
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TermConnState.class.getSimpleName() + "[", "]")
                .add("simNo='" + simNo + "'")
                .add("connTimes=" + connTimes)
                .add("connAt=" + connAt)
                .add("protoVer=" + protoVer)
                .add("lastActive=" + lastActive)
                .add("trkCnt=" + trkCnt)
                .add("acc=" + acc)
                .add("spd=" + spd)
                .add("vidAlm=" + vidAlm)
                .add("latestTrk=" + latestTrk)
                .toString();
    }
}
