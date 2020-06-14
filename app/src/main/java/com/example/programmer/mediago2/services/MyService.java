package com.example.programmer.mediago2.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;

import com.example.programmer.mediago2.App;
import com.example.programmer.mediago2.R;
import com.example.programmer.mediago2.recivers.MyReceiver;

public class MyService extends Service {
    public static Notification notification;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent intentStart = new Intent(getApplicationContext(), MyReceiver.class);
        intentStart.setAction("start");
        Intent intentStop = new Intent(getApplicationContext(), MyReceiver.class);
        intentStop.setAction("stop");
        Intent intentNext = new Intent(getApplicationContext(), MyReceiver.class);
        intentNext.setAction("next");
        Intent intentBack = new Intent(getApplicationContext(), MyReceiver.class);
        intentBack.setAction("back");


        Notification.Action actionStart = new Notification.Action(R.drawable.ic_play, "start", PendingIntent
                .getBroadcast(this, 1, intentStart, 0));
        Notification.Action actionStop = new Notification.Action(R.drawable.ic_pause, "stop", PendingIntent
                .getBroadcast(this, 1, intentStop, 0));
        Notification.Action actionNext = new Notification.Action(R.drawable.ic_next, "next", PendingIntent
                .getBroadcast(this, 1, intentNext, 0));
        Notification.Action actionBack = new Notification.Action(R.drawable.ic_back, "back", PendingIntent
                .getBroadcast(this, 1, intentBack, 0));


        notification = new Notification.Builder(this, App.CH_ID)
                .setSmallIcon(R.drawable.not_small1)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.aritist))
                .setContentText(intent.getStringExtra("name"))


                .addAction(actionBack)
                .addAction(actionStop)
                .addAction(actionNext)
                .setStyle(new Notification.MediaStyle().setShowActionsInCompactView(0, 1, 2))

                .build();


        startForeground(5, notification);

        return START_NOT_STICKY;
    }
}
