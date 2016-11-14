package com.jnj.android.rideraid.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.jnj.android.rideraid.R;
import com.jnj.android.rideraid.RiderAidApplication;
import com.jnj.android.rideraid.ant.AntBikeDevice;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class SplashActivity extends Activity {
    AntBikeDevice device = (AntBikeDevice) RiderAidApplication.ant;

    @BindView(R.id.iv_splash_image)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);

        try {
            String files[] = getAssets().list("splash");
            for (String file : files) {
                Log.wtf("Splash", "File: " + file);
            }

            imageView.setImageDrawable(
                    Drawable.createFromStream(getAssets().open("splash/cycle_illustration_01.png"), null));
        } catch (IOException ioex) {
            Log.e("SplashScreen", "Can't load splash image", ioex);
        }

        if (device == null || device.isActive()) {
            startTelemetry();
            return;
        }

        device.activate(this)
                .subscribe(active -> {
                    if (active) {
                        startTelemetry();
                        return;
                    }
                    Toast.makeText(this, "Error while activating an ANT+ device", Toast.LENGTH_LONG);
                });
    }

    private void startTelemetry() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}
