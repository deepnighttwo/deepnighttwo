package com.ctrip.clog.domain;

import java.util.Map;
import java.util.SortedMap;

/**
 * User: mzang
 * Date: 11/4/13
 * Time: 4:57 PM
 */
public class LogEntry extends BaseEntry {
    /**
     * log title
     */
    protected String title;
    /**
     * log message
     */
    protected String message;
    /**
     * where log comes from, class name probably
     */
    protected String source;
    /**
     * host ip / name where log comes from
     */
    protected String hostIp;
    /**
     * thread name / id which generate the log
     */
    protected String threadName;
    /**
     * trace id
     */
    protected long traceId;

    /**
     * attributes map
     */
    protected SortedMap<String, String> attributes;

    private boolean hasAttributes() {
        return attributes != null && attributes.size() > 0;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public long getTraceId() {
        return traceId;
    }

    public void setTraceId(long traceId) {
        this.traceId = traceId;
    }

    public SortedMap<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(SortedMap<String, String> attributes) {
        this.attributes = attributes;
    }
}
