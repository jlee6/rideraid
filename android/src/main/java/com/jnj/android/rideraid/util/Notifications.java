package com.jnj.android.rideraid.util;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.jnj.android.rideraid.R;

import java.util.Random;

public class Notifications {
    private static int notificationID  = new Random().nextInt();

    public static void showNotification(Activity activity) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity)
                .setSmallIcon(R.drawable.crank_512)
                .setContentTitle("Rider Aid")
                .setContentText("Ride started");

        Intent  originActivity = new Intent(activity, activity.getClass());
        PendingIntent pending = PendingIntent.getActivity(activity, 0, originActivity, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pending);

        NotificationManagerCompat.from(activity).notify(notificationID, builder.build());
    }

    public static void hideNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager == null) {
            return;
        }

        notificationManager.cancel(notificationID);
    }
}
