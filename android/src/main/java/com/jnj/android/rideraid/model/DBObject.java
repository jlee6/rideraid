package com.jnj.android.rideraid.model;

@SuppressWarnings("WeakerAccess")
public abstract class DBObject {
    private long sqlId = -1;
    private long timestamp = System.currentTimeMillis();
    private long sessionId;

    protected DBObject(long sqlId, long timestamp, long sessionId) {
        this.sqlId = sqlId;
        this.timestamp = timestamp;
        this.sessionId = sessionId;
    }

    public long getSqlId() {
        return sqlId;
    }

    public void setSqlId(long sqlId) {
        this.sqlId = sqlId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }
}
