package com.android.sss;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import java.util.Random;

/**
 * Created by optimus158 on 26-Aug-15.
 */
public class NotificationListener extends GcmListenerService {
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("text_message");
        Log.i("SN", message);
        if (message != null) {
            sendNotification(message);
        }
    }

    private void sendNotification(String message) {
        int notification = new Random().nextInt(100);
        Intent intent = new Intent(SSSApplication.getAppContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, notification, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.sss_logo)
                .setContentTitle("GCM Message")
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(SSSApplication.getAppContext().NOTIFICATION_SERVICE);

        notificationManager.notify(notification, notificationBuilder.build());
    }
}
