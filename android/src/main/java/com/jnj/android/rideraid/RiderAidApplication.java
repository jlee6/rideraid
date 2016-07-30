package com.jnj.android.rideraid;

import android.app.Application;
import android.content.Context;

import com.jnj.android.rideraid.ant.AntDevice;
import com.jnj.android.rideraid.ant.AntGarminGSC10;
import com.jnj.android.rideraid.ant.Tire;

public class RiderAidApplication extends Application {
    // TODO: Properly encapsulate ant device
    public static final AntDevice ant = new AntGarminGSC10(Tire.CIRCUMFERENCE_MM_23);
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
    }
}
