package com.ctrip.clog.domain;

/**
 * User: mzang
 * Date: 11/4/13
 * Time: 4:55 PM
 */
public class BaseEntry {
    protected long timestamp;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
