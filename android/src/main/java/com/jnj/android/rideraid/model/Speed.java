package com.jnj.android.rideraid.model;

public class Speed extends DBObject {
    private long speed;

    private Speed(long sqlId, long timestamp, long sessionId, long speed) {
        super(sqlId, timestamp, sessionId);

        this.speed = speed;
    }

    public long getSpeed() {
        return speed;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public static class builder {
        long id;
        long ts;
        long ss;
        long spd;

        public builder setId(long id) {
            this.id = id;
            return this;
        }

        public builder setTimestamp(long ts) {
            this.ts = ts;
            return this;
        }

        public builder setSession(long ss) {
            this.ss = ss;
            return this;
        }

        public builder setSpeed(long spd) {
            this.spd = spd;
            return this;
        }

        public Speed build() {
            return new Speed(id, ts, ss, spd);
        }
    }
}
