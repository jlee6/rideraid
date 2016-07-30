package com.jnj.android.rideraid.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.jnj.android.rideraid.RiderAidApplication;
import com.jnj.android.rideraid.model.Cadence;
import com.jnj.android.rideraid.model.Location;
import com.jnj.android.rideraid.model.Speed;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import rx.schedulers.Schedulers;

@SuppressWarnings("UnusedReturnValue")
public class Database {
    private static Database instance;
    private SqlBrite brite;
    private BriteDatabase database;
    private SqlHelper helper;

    private Database() {
        initialize(RiderAidApplication.getContext());
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private void initialize(Context context) {
        helper = new SqlHelper(context);

        brite = SqlBrite.create();
        database = brite.wrapDatabaseHelper(helper, Schedulers.io());
    }

    public Speed save(@NonNull Speed speed) {
        ContentValues values = new ContentValues();

        values.put(DatabaseConst.TABLE_COLUMN_ID, speed.getSqlId() < 0 ? null : speed.getSqlId());
        values.put(DatabaseConst.TABLE_COLUMN_TIME, speed.getTimestamp());
        values.put(DatabaseConst.TABLE_COLUMN_SESSION, speed.getSessionId());
        values.put(DatabaseConst.TABLE_COLUMN_SPEED, speed.getSpeed());

        speed.setSqlId(database.insert(DatabaseConst.TABLE_SPEED, values, SQLiteDatabase.CONFLICT_REPLACE));

        return speed;
    }

    public Cadence save(@NonNull Cadence cadence) {
        ContentValues values = new ContentValues();

        values.put(DatabaseConst.TABLE_COLUMN_ID, cadence.getSqlId() < 0 ? null : cadence.getSqlId());
        values.put(DatabaseConst.TABLE_COLUMN_TIME, cadence.getTimestamp());
        values.put(DatabaseConst.TABLE_COLUMN_SESSION, cadence.getSessionId());
        values.put(DatabaseConst.TABLE_COLUMN_CADENCE, cadence.getCadence());

        cadence.setSqlId(database.insert(DatabaseConst.TABLE_CADENCE, values, SQLiteDatabase.CONFLICT_REPLACE));

        return cadence;
    }

    public Location save(@NonNull Location location) {
        ContentValues values = new ContentValues();

        values.put(DatabaseConst.TABLE_COLUMN_ID, location.getSqlId() < 0 ? null : location.getSqlId());
        values.put(DatabaseConst.TABLE_COLUMN_TIME, location.getTimestamp());
        values.put(DatabaseConst.TABLE_COLUMN_SESSION, location.getSessionId());
        values.put(DatabaseConst.TABLE_COLUMN_LAT, location.getLat());
        values.put(DatabaseConst.TABLE_COLUMN_LNG, location.getLng());
        values.put(DatabaseConst.TABLE_COLUMN_ALT, location.getAlt());

        location.setSqlId(database.insert(DatabaseConst.TABLE_LOCATION, values, SQLiteDatabase.CONFLICT_REPLACE));

        return location;
    }

    public Speed getSpeed(Cursor cursor, int position) {
        if (cursor == null || cursor.isNull(position)) {
            return null;
        }

        cursor.moveToPosition(position);

        int sqlId = cursor.getColumnIndex(DatabaseConst.TABLE_COLUMN_ID);
        int timestamp = cursor.getColumnIndex(DatabaseConst.TABLE_COLUMN_TIME);
        int session = cursor.getColumnIndex(DatabaseConst.TABLE_COLUMN_SESSION);
        int speed = cursor.getColumnIndex(DatabaseConst.TABLE_COLUMN_SPEED);

        return new Speed.builder()
                .setId(cursor.getLong(sqlId))
                .setTimestamp(cursor.getLong(timestamp))
                .setSession(cursor.getLong(session))
                .setSpeed(cursor.getLong(speed))
                .build();
    }

    public Cadence getCadence(Cursor cursor, int position) {
        if (cursor == null || cursor.isNull(position)) {
            return null;
        }

        cursor.moveToPosition(position);

        int sqlId = cursor.getColumnIndex(DatabaseConst.TABLE_COLUMN_ID);
        int timestamp = cursor.getColumnIndex(DatabaseConst.TABLE_COLUMN_TIME);
        int session = cursor.getColumnIndex(DatabaseConst.TABLE_COLUMN_SESSION);
        int cadence = cursor.getColumnIndex(DatabaseConst.TABLE_COLUMN_CADENCE);

        return new Cadence.builder()
                .setId(cursor.getLong(sqlId))
                .setTimestamp(cursor.getLong(timestamp))
                .setSession(cursor.getLong(session))
                .setCadence(cursor.getLong(cadence))
                .build();
    }

    public Location getLocation(Cursor cursor, int position) {
        if (cursor == null || cursor.isNull(position)) {
            return null;
        }

        cursor.moveToPosition(position);

        int sqlId = cursor.getColumnIndex(DatabaseConst.TABLE_COLUMN_ID);
        int timestamp = cursor.getColumnIndex(DatabaseConst.TABLE_COLUMN_TIME);
        int session = cursor.getColumnIndex(DatabaseConst.TABLE_COLUMN_SESSION);
        int latitude = cursor.getColumnIndex(DatabaseConst.TABLE_COLUMN_LAT);
        int longitude = cursor.getColumnIndex(DatabaseConst.TABLE_COLUMN_LNG);
        int altitude = cursor.getColumnIndex(DatabaseConst.TABLE_COLUMN_ALT);

        return new Location.builder()
                .setId(cursor.getLong(sqlId))
                .setTimestamp(cursor.getLong(timestamp))
                .setSession(cursor.getLong(session))
                .setLat(cursor.getDouble(latitude))
                .setLong(cursor.getDouble(longitude))
                .setAlt(cursor.getDouble(altitude))
                .build();
    }

    private static class SqlHelper extends SQLiteOpenHelper {
        public SqlHelper(Context context) {
            super(context, DatabaseConst.DATABASE_FILENAME, null, DatabaseConst.VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DatabaseConst.QUERY_CREATE_TABLE_SPEED);
            db.execSQL(DatabaseConst.QUERY_CREATE_TABLE_CADENCE);
            db.execSQL(DatabaseConst.QUERY_CREATE_TABLE_LOCATION);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
            final String query = "drop table if exists ";

            db.execSQL(query + DatabaseConst.TABLE_SPEED);
            db.execSQL(query + DatabaseConst.TABLE_CADENCE);
            db.execSQL(query + DatabaseConst.TABLE_LOCATION);

            onCreate(db);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer) {
            onUpgrade(db, oldVer, newVer);
        }
    }
}
