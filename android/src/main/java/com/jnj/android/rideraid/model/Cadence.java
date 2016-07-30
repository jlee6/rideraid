package com.jnj.android.rideraid.model;

public class Cadence extends DBObject {
    private long cadence;

    private Cadence(long sqlId, long timestamp, long sessionId, long cad) {
        super(sqlId, timestamp, sessionId);
        cadence = cad;
    }

    public long getCadence() {
        return cadence;
    }

    public void setCadence(long cadence) {
        this.cadence = cadence;
    }

    public static class builder {
        long id;
        long ts;
        long ss;
        long cad;

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

        public builder setCadence(long cad) {
            this.cad = cad;
            return this;
        }

        public Cadence build() {
            return new Cadence(id, ts, ss, cad);
        }
    }
}
