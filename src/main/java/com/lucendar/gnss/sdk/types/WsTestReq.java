package com.lucendar.gnss.sdk.types;

import java.util.StringJoiner;

public class WsTestReq {

    private String queue;
    private String msg;

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", WsTestReq.class.getSimpleName() + "[", "]")
                .add("queue='" + queue + "'")
                .add("msg='" + msg + "'")
                .toString();
    }
}
