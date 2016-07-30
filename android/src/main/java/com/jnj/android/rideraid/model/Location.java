package com.jnj.android.rideraid.model;

public class Location extends DBObject {
    private double lat;
    private double lng;
    private double alt;

    private Location(long sqlId, long timestamp, long sessionId, double lat, double lng, double alt) {
        super(sqlId, timestamp, sessionId);

        this.lat = lat;
        this.lng = lng;
        this.alt = alt;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getAlt() {
        return alt;
    }

    public void setAlt(double alt) {
        this.alt = alt;
    }

    public static class builder {
        long id;
        long ts;
        long ss;
        double lat;
        double lng;
        double alt;

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

        public builder setLat(double lat) {
            this.lat = lat;
            return this;
        }

        public builder setLong(double lng) {
            this.lng = lng;
            return this;
        }

        public builder setAlt(double alt) {
            this.alt = alt;
            return this;
        }

        public Location build() {
            return new Location(id, ts, ss, lat, lng, lng);
        }
    }
}

