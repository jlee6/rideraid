package com.jnj.android.rideraid.util;

import com.jnj.android.rideraid.R;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class UnitUtilsTest {
    @Test
    public void unitConversionToKMH() throws Exception {
        Assert.assertEquals(3.6,
                UnitUtils.convertCalcSpeed(1, R.string.unit_speed_kmh),
                0.00001);
    }

    @Test
    public void unitConversionToMPH() throws Exception {
        Assert.assertEquals(2.23694,
                UnitUtils.convertCalcSpeed(1, R.string.unit_speed_mph),
                0.00001);
    }
}