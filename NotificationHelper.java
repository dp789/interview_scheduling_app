package com.example.tanzeem.internity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;

public class NotificationHelper extends ContextWrapper {
    private static final String CHANNEL_ID = "com.example.tanzeem.internity";
    private static final String CHANNEL_NAME = "Tanzeem Channel";
    private NotificationManager manager;

    public NotificationHelper(Context base) {
        super(base);
        createChannels();
    }

    private void createChannels(){

        NotificationChannel newChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            newChannel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            newChannel.enableLights(true);
            newChannel.enableVibration(true);
            newChannel.setLightColor(Color.GREEN);
            newChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            getManager().createNotificationChannel(newChannel);
        }

    }

    public NotificationManager getManager() {
        if(manager == null)
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        return manager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getChannelNotification() {
        return new Notification.Builder(getApplicationContext(),CHANNEL_ID)
                .setContentText("New card is created")
                .setContentTitle("Card Notification")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true);
    }
}
