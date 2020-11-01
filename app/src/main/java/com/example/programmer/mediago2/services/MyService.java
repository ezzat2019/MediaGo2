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
import com.example.programmer.mediago2.MainActivity;
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







        notification = new Notification.Builder(this, App.CH_ID)
                .setSmallIcon(R.drawable.not_small1)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.aritist))
                .setContentText(intent.getStringExtra("name"))

                .build();


        startForeground(5, notification);

        return START_NOT_STICKY;
    }
}
