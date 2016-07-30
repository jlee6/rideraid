package com.jnj.android.rideraid.presenter.module;

import android.util.Log;

import com.jnj.android.rideraid.ant.AntBikeDevice;
import com.jnj.android.rideraid.ant.AntDevice;
import com.jnj.android.rideraid.database.Database;
import com.jnj.android.rideraid.model.Cadence;
import com.jnj.android.rideraid.model.Speed;
import com.jnj.android.rideraid.presenter.TelemetryPresenter;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class TeleSensor implements TelemetryPresenter {
    private final AntBikeDevice device;
    private final TelemetryPresenter.View view;
    private Subscriber<AntBikeDevice.BikeEvent> eventSubscriber;

    public TeleSensor(TelemetryPresenter.View view, AntBikeDevice device) {
        this.view = view;
        this.device = device;
    }

    @Override
    public boolean isActive() {
        return eventSubscriber != null;
    }

    @Override
    public void start(long sessionId) {
        if (device == null) {
            return;
        }

        initializeBikeEventSubscriber(sessionId);
    }

    @Override
    public void stop() {
        if (eventSubscriber == null || !eventSubscriber.isUnsubscribed()) {
            return;
        }

        eventSubscriber.unsubscribe();
        eventSubscriber = null;
    }

    private void initializeBikeEventSubscriber(final long sessionId) {
        eventSubscriber = new Subscriber<AntBikeDevice.BikeEvent>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TeleSensor", "Unable to process bike event", e);
            }

            @Override
            public void onNext(AntBikeDevice.BikeEvent bikeEvent) {
                long value = bikeEvent.getValue();
                switch (bikeEvent.getType()) {
                    case AntBikeDevice.ANT_DEVICE_TYPE_SPEED:
                        Database.getInstance()
                                .save(new Speed.builder()
                                        .setSession(sessionId)
                                        .setSpeed(value)
                                        .build());
                        view.updateSpeed(value);
                        break;
                    case AntDevice.ANT_DEVICE_TYPE_CADENCE:
                        Database.getInstance()
                                .save(new Cadence.builder()
                                        .setSession(sessionId)
                                        .setCadence(value)
                                        .build());
                        view.updateCadence(value);
                        break;
                }
            }
        };

        device.getBikeEventObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(eventSubscriber);
    }
}
