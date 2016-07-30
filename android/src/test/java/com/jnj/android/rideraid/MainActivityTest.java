package com.jnj.android.rideraid;

import android.support.v4.app.Fragment;
import android.view.View;

import com.jnj.android.rideraid.ui.activity.MainActivity;
import com.jnj.android.rideraid.ui.fragment.Telemetry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class MainActivityTest {
    MainActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(MainActivity.class).create().start().resume().get();
    }

    /**
     * Reports failure when activity is null
     */
    @Test
    public void uiSanityTest() throws Exception {
        assertNotNull(activity);

        Fragment fragment = new Telemetry();
        SupportFragmentTestUtil.startFragment(fragment);

        View view = fragment.getView();
        assertNotNull(view);

        assertNotNull(view.findViewById(R.id.tv_time_value));
        assertNotNull(view.findViewById(R.id.tv_cad_value));
        assertNotNull(view.findViewById(R.id.tv_spd_value));
    }
}