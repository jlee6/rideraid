package com.jnj.android.rideraid.presenter;

public interface PositionPresenter extends PresenterActions {
    interface View {
        void updateDistance(double distance);
    }
}
