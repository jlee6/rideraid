package com.jnj.android.rideraid.util;

import android.view.Window;
import android.view.WindowManager;

public class WindowsUtils {
    private WindowsUtils() {
    }

    public static void lockScreenDim(Window window, boolean lock) {
        if (lock) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }
}
