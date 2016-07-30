package com.jnj.android.rideraid.presenter.module;

import com.jnj.android.rideraid.presenter.TimePresenter;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Tick implements TimePresenter {
    private final TimePresenter.View view;
    private Subscriber<Long> tickHandler;

    public Tick(TimePresenter.View view) {
        this.view = view;
    }

    @Override
    public boolean isActive() {
        return tickHandler != null;
    }

    @Override
    public void start(long sessionId) {
        if (tickHandler != null) {
            return;
        }

        setupTickHandler();

        Observable.interval(1000, TimeUnit.MILLISECONDS, Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tickHandler);
    }

    @Override
    public void stop() {
        if (tickHandler == null) {
            return;
        }

        tickHandler.onCompleted();
        tickHandler.unsubscribe();
        tickHandler = null;
    }

    private void setupTickHandler() {
        tickHandler = new Subscriber<Long>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                view.updateTime(0);
            }

            @Override
            public void onNext(Long time) {
                view.updateTime(time);
            }
        };
    }
}
