package com.jnj.android.rideraid;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 * TODO: Update to use with Instrumentation Registry
 */
@RunWith(AndroidJUnit4.class)
public class RiderAidInstrumentedTest {
    private Context context;

    @Before
    public void useAppContext() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void validApplicationContextLoaded() throws Exception {
        assertEquals("com.jnj.android.rideraid", context.getPackageName());
    }
}