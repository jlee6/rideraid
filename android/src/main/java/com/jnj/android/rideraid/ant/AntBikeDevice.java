package com.jnj.android.rideraid.ant;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

public abstract class AntBikeDevice implements AntDevice {
    protected final BigDecimal bikeTireCircumference;
    private final List<Subscriber<BikeEvent>> subscriptions;
    protected int antDeviceType;
    protected EventHandler eventHandler;

    public AntBikeDevice(@Tire.TireCircumferenceMM long tire) {
        bikeTireCircumference = new BigDecimal(tire);
        subscriptions = new LinkedList<>();
    }

    public static BikeEvent createEvent(int type, long value) {
        return new BikeEvent(type, value);
    }

    public Observable<BikeEvent> getBikeEventObservable() {
        return Observable.create(subscriber -> eventHandler = subscriber::onNext);
    }

    @Override
    public int getType() {
        return antDeviceType;
    }

    interface EventHandler {
        void onEvent(BikeEvent event);
    }

    public static class BikeEvent {
        private final int type;
        private final long value;

        private BikeEvent(int type, long value) {
            this.type = type;
            this.value = value;
        }

        public int getType() {
            return type;
        }

        public long getValue() {
            return value;
        }
    }
}
