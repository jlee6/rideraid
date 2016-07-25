package com.jnj.android.rideraid.presenter.module;

import android.util.Log;

import com.jnj.android.rideraid.ant.AntBikeDevice;
import com.jnj.android.rideraid.ant.AntDevice;
import com.jnj.android.rideraid.presenter.TelemetryPresenter;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class TeleSensor implements TelemetryPresenter {
    private Subscriber<AntBikeDevice.BikeEvent> eventSubscriber;
    private AntBikeDevice device;
    private TelemetryPresenter.View view;

    public TeleSensor(TelemetryPresenter.View view, AntBikeDevice device) {
        this.view = view;
        this.device = device;
    }

    @Override
    public boolean isActive() {
        return eventSubscriber != null;
    }

    @Override
    public void start() {
        if (device == null) {
            return;
        }

        initializeBikeEventSubscriber();
    }

    @Override
    public void stop() {
        if (eventSubscriber == null || !eventSubscriber.isUnsubscribed()) {
            return;
        }

        eventSubscriber.unsubscribe();
        eventSubscriber = null;
    }

    private void initializeBikeEventSubscriber() {
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
                switch (bikeEvent.getType()) {
                    case AntBikeDevice.ANT_DEVICE_TYPE_SPEED:
                        view.updateSpeed(bikeEvent.getValue());
                        break;
                    case AntDevice.ANT_DEVICE_TYPE_CADENCE:
                        view.updateCadence(bikeEvent.getValue());
                        break;
                }
            }
        };

        device.getBikeEventObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(eventSubscriber);
    }
}
