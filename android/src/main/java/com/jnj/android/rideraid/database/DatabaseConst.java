package com.jnj.android.rideraid.database;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SuppressWarnings("WeakerAccess")
public class DatabaseConst {
    public static final String DATABASE_FILENAME = "telemetry.db";

    public static final int VERSION = 1;

    public static final String TABLE_SPEED = "speed";
    public static final String TABLE_CADENCE = "cadence";
    public static final String TABLE_LOCATION = "location";

    public static final String TABLE_COLUMN_ID = "id";
    public static final String TABLE_COLUMN_TIME = "time";
    public static final String TABLE_COLUMN_SESSION = "session";
    public static final String TABLE_COLUMN_SPEED = "speed";
    public static final String[] TABLE_SPEED_COLUMN_PROJECTION = {
            TABLE_COLUMN_ID, TABLE_COLUMN_TIME, TABLE_COLUMN_SESSION, TABLE_COLUMN_SPEED
    };
    public static final String QUERY_CREATE_TABLE_SPEED =
            "create table " + TABLE_SPEED + " (" +
                    TABLE_COLUMN_ID + " integer primary key autoincrement, " +
                    TABLE_COLUMN_TIME + " integer, " +
                    TABLE_COLUMN_SESSION + " integer, " +
                    TABLE_COLUMN_SPEED + " integer)";
    public static final String TABLE_COLUMN_CADENCE = "cadence";
    public static final String[] TABLE_CADENCE_COLUMN_PROJECTION = {
            TABLE_COLUMN_ID, TABLE_COLUMN_TIME, TABLE_COLUMN_SESSION, TABLE_COLUMN_CADENCE
    };
    public static final String QUERY_CREATE_TABLE_CADENCE =
            "create table " + TABLE_SPEED + " (" +
                    TABLE_COLUMN_ID + " integer primary key autoincrement, " +
                    TABLE_COLUMN_TIME + " integer, " +
                    TABLE_COLUMN_SESSION + " integer," +
                    TABLE_COLUMN_CADENCE + " integer)";
    public static final String TABLE_COLUMN_LAT = "latitude";
    public static final String TABLE_COLUMN_LNG = "longitude";
    public static final String TABLE_COLUMN_ALT = "altitude";
    public static final String[] TABLE_LOCATION_COLUMN_PROJECTION = {
            TABLE_COLUMN_ID, TABLE_COLUMN_TIME, TABLE_COLUMN_SESSION,
            TABLE_COLUMN_LAT, TABLE_COLUMN_LNG, TABLE_COLUMN_ALT
    };
    public static final String QUERY_CREATE_TABLE_LOCATION =
            "create table " + TABLE_SPEED + " (" +
                    TABLE_COLUMN_ID + " integer primary key autoincrement, " +
                    TABLE_COLUMN_TIME + " integer, " +
                    TABLE_COLUMN_SESSION + " integer," +
                    TABLE_COLUMN_LAT + " integer, " +
                    TABLE_COLUMN_LNG + " integer, " +
                    TABLE_COLUMN_ALT + " integer)";

    // TODO: really need these?
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            TABLE_COLUMN_ID,
            TABLE_COLUMN_TIME,
            TABLE_COLUMN_SESSION,
            TABLE_COLUMN_SPEED
    })
    public @interface TABLE_SPEED_COLUMN {
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            TABLE_COLUMN_ID,
            TABLE_COLUMN_TIME,
            TABLE_COLUMN_SESSION,
            TABLE_COLUMN_CADENCE
    })
    public @interface TABLE_CADENCE_COLUMN {
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            TABLE_COLUMN_ID,
            TABLE_COLUMN_TIME,
            TABLE_COLUMN_SESSION,
            TABLE_COLUMN_LAT,
            TABLE_COLUMN_LNG,
            TABLE_COLUMN_ALT
    })
    public @interface TABLE_LOCATION_COLUMN {
    }
}
