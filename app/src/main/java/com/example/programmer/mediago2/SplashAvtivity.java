package com.example.programmer.mediago2;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.app.NotificationCompat;
import android.support.v4.media.app.NotificationCompat.MediaStyle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.example.programmer.mediago2.services.MyService;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class SplashAvtivity extends AppCompatActivity {


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_avtivity);
        chechExternalPerrmisson();











    }



    private void chechExternalPerrmisson() {
        Dexter.withActivity(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE
        )
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                /* Create an Intent that will start the Menu-Activity. */
                                ActivityOptions options = (ActivityOptions) ActivityOptions.makeSceneTransitionAnimation(SplashAvtivity.this);
                                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(mainIntent, options.toBundle());
                                finish();
                            }
                        }, 1500);

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        finish();

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {


                        token.continuePermissionRequest();

                    }
                }).check();
    }

}
