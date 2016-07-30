package com.jnj.android.rideraid.presenter.module;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.WindowManager;

import com.jnj.android.rideraid.presenter.PositionPresenter;

import rx.Observable;

@SuppressWarnings("MissingPermission")
public class Position implements PositionPresenter {
    private final Context context;
    private final int permission;
    private final LocationManager locationManager;
    private boolean active;
    private Location location;
    private PositionUpdateListener listener;
    private PositionPresenter.View view;
    private final LocationListener locationListener = new LocationListener() {
        double distance;
        Location last;

        @Override
        public void onLocationChanged(Location location) {
            if (last == null) {
                distance = 0;
                last = location;
            }

            if (listener == null) {
                return;
            }

            view.updateDistance(distance += calculateDistance(last, location));
            listener.onPositionChanged(location);
            last = location;
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            active = true;
            reset();
            String locationProvider = locationManager.getBestProvider(createFineCriteria(), true);
            locationManager.requestLocationUpdates(locationProvider, 0, 0f, locationListener);
        }

        @Override
        public void onProviderDisabled(String provider) {
            active = false;
            reset();
            locationManager.removeUpdates(locationListener);
        }

        private void reset() {
            last = null;
            distance = 0;
        }
    };

    public Position(Context context, PositionPresenter.View view) {
        this.context = context;

        permission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    private static Criteria createFineCriteria() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(true);
        criteria.setBearingRequired(true);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        return criteria;
    }

    private static double calculateDistance(Location from, Location to) {
        return (from != null) ? to.distanceTo(from) : 0;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void start(long sessionId) {
        initializeLocationService(sessionId);
    }

    @Override
    public void stop() {
        dispose();
    }

    public Observable<Location> onPositionChanged() {
        return Observable.create(subscriber -> listener = subscriber::onNext);
    }

    public void dispose() {
        if (permission != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager.removeUpdates(locationListener);
    }

    private void initializeLocationService(final long sessionId) {
        if (permission != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        String locationProvider = locationManager.getBestProvider(createFineCriteria(), true);

        if (!TextUtils.isEmpty(locationProvider) && !locationProvider.equals("passive")) {
            locationManager.requestLocationUpdates(locationProvider, 0, 0f, locationListener);
            return;
        }

        AlertDialog alert = new AlertDialog.Builder(context)
                .setTitle("Enable Location Provider")
                .setMessage("Unable to find location provider\r\nWould like to enable location settings now?")
                .setPositiveButton("Enable", (dialog, which) -> {
                    Intent settings = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    settings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(settings);
                })
                .setNegativeButton("Leave", null)
                .create();

        alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alert.show();
    }

    private interface PositionUpdateListener {
        void onPositionChanged(Location location);
    }
}
