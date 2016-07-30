package com.jnj.android.rideraid.presenter;

public interface PresenterActions {
    boolean isActive();

    void start(long sessionId);
    void stop();
}
