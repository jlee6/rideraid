package com.jnj.android.rideraid.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.jnj.android.rideraid.R;
import com.jnj.android.rideraid.RiderAidApplication;
import com.jnj.android.rideraid.ant.AntBikeDevice;
import com.jnj.android.rideraid.ant.AntDevice;
import com.jnj.android.rideraid.ant.AntGarminGSC10;
import com.jnj.android.rideraid.ant.Tire;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class SplashActivity extends Activity {
    AntBikeDevice device = (AntBikeDevice) RiderAidApplication.ant;

    @BindView(R.id.iv_splash_image)
    ImageView ivSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // TODO: investigate this doesn't happen?
//        ButterKnife.bind(this);

        ImageView image = (ImageView) findViewById(R.id.iv_splash_image);

        try {
            String files[] = getAssets().list("splash");
            for (String file : files) {
                Log.wtf("Splash", "File: " + file);
            }

            image.setImageDrawable(
                    Drawable.createFromStream(getAssets().open("splash/cycle_illustration_01.png"), null));
        } catch (IOException ioex) {
            Log.e("SplashScreen", "Can't load splash image", ioex);
        }

        if (device == null) {
            throw new IllegalStateException("Required device is not found");
        }

        if (device.isActive()) {
            startTelemetry();
            return;
        }

        device.getBikeEventObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(event -> {
                    if (event.getType() == AntBikeDevice.ANT_DEVICE_ACTIVE) {
                            startTelemetry();
                    }
                }, throwable -> Log.e("Splash", "Can't start application"));
        device.activate(this);
    }

    private void startTelemetry() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}