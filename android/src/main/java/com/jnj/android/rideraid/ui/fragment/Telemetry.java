package com.jnj.android.rideraid.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jnj.android.rideraid.R;
import com.jnj.android.rideraid.RiderAidApplication;
import com.jnj.android.rideraid.ant.AntBikeDevice;
import com.jnj.android.rideraid.presenter.PositionPresenter;
import com.jnj.android.rideraid.presenter.PresenterActions;
import com.jnj.android.rideraid.presenter.TelemetryPresenter;
import com.jnj.android.rideraid.presenter.TimePresenter;
import com.jnj.android.rideraid.presenter.module.Position;
import com.jnj.android.rideraid.presenter.module.TeleSensor;
import com.jnj.android.rideraid.presenter.module.Tick;
import com.jnj.android.rideraid.util.WindowsUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressWarnings("WeakerAccess")
public class Telemetry extends Fragment
        implements TimePresenter.View, TelemetryPresenter.View, PositionPresenter.View {
    private final AntBikeDevice device = (AntBikeDevice) RiderAidApplication.ant;
    @BindView(R.id.tv_time_value)
    TextView tvTimer;
    @BindView(R.id.tv_cad_value)
    TextView tvCadence;
    @BindView(R.id.tv_spd_value)
    TextView tvSpeed;
    @BindView(R.id.tv_dist_value)
    TextView tvDistance;
    private Unbinder unbinder;
    private PresenterActions[] presenters;
    private SimpleDateFormat formatter;

    @SuppressLint("SimpleDateFormat")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.setDebug(true);

        // Suppressing simple data format since the locale doesn't matter
        formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

        presenters = new PresenterActions[]{
                new Tick(this),
                new TeleSensor(this, device),
                new Position(getContext(), this)};

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.telemetry_fragment, container, false);

        // TODO: investigate butterknife doesn't take effect
        //unbinder = ButterKnife.bind(this, view);

        tvTimer = (TextView) view.findViewById(R.id.tv_time_value);
        tvCadence = (TextView) view.findViewById(R.id.tv_cad_value);
        tvSpeed = (TextView) view.findViewById(R.id.tv_spd_value);
        tvDistance = (TextView) view.findViewById(R.id.tv_dist_value);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_main, menu);

        PresenterActions p = presenters[0];
        menu.findItem(R.id.action_record).setVisible(!p.isActive());
        menu.findItem(R.id.action_stop).setVisible(p.isActive());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_record:
                long sessionId = System.currentTimeMillis();
                for (PresenterActions p : presenters) {
                    p.start(sessionId);
                }

                WindowsUtils.lockScreenDim(getActivity().getWindow(), true);
                getActivity().invalidateOptionsMenu();
                return true;
            case R.id.action_stop:
                for (PresenterActions p : presenters) {
                    p.stop();
                }

                WindowsUtils.lockScreenDim(getActivity().getWindow(), false);
                getActivity().invalidateOptionsMenu();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateTime(long time) {
        tvTimer.setText(formatter.format(new Date(time * 1000)));
    }

    @Override
    public void updateCadence(long cad) {
        tvCadence.setText(String.valueOf(cad));
    }

    @Override
    public void updateSpeed(long spd) {
        tvSpeed.setText(String.valueOf(spd));
    }

    @Override
    public void updateDistance(double distance) {
        tvDistance.setText(String.valueOf(tvDistance));
    }
}
