package com.jnj.android.rideraid.presenter;

public interface TimePresenter extends PresenterActions {
    interface View {
        void updateTime(long second);
    }
}
