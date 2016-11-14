package com.jnj.android.rideraid.ant;

import android.app.Activity;

import io.reactivex.Observable;

public interface AntDevice {
    int ANT_DEVICE_ACTIVE = 0xFF;
    int ANT_DEVICE_ERROR = -1;

    int ANT_DEVICE_TYPE_CADENCE = 0;
    int ANT_DEVICE_TYPE_SPEED = 1;

    int getType();
    void release();

    boolean isActive();
    Observable<Boolean> activate(final Activity activity);
}
