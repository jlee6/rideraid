package com.jnj.android.rideraid.presenter;

public interface TelemetryPresenter extends PresenterActions {
    interface View {
        void updateCadence(long cad);
        void updateSpeed(long spd);
    }
}
