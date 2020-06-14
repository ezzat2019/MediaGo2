package com.example.programmer.mediago2.recivers;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.programmer.mediago2.App;
import com.example.programmer.mediago2.MainActivity;
import com.example.programmer.mediago2.R;
import com.example.programmer.mediago2.fragments.FragSongs;
import com.example.programmer.mediago2.services.MyService;

public class MyReceiver extends BroadcastReceiver {
private Context context;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(final Context context, Intent intent) {
        this.context=context;
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        if (intent.getAction().toString() == "start")
        {


            Toast.makeText(context, "Start", Toast.LENGTH_SHORT).show();
        }else if (intent.getAction().toString() == "stop")
        {
            FragSongs.btn_stop.setVisibility(View.GONE);
            FragSongs.btn_stop.setVisibility(View.VISIBLE);


            FragSongs.mediaPlayer.start();
        }else if (intent.getAction().toString() == "next")
        {


                    if (FragSongs.poss == null)
                        FragSongs.poss = MainActivity.pos;

                    if (FragSongs.poss >= FragSongs.list.size() - 1)
                        FragSongs.poss = 0;
                    else {
                        ++FragSongs.poss;
                    }


                    if (FragSongs.mediaPlayer != null)
                        FragSongs.mediaPlayer.reset();
                    Uri uri = Uri.parse(FragSongs.list.get(FragSongs.poss).getId());
                    FragSongs.mediaPlayer = MediaPlayer.create(context, uri);
                    if (MainActivity.mediaPlayer != null) {
                        MainActivity.mediaPlayer.reset();
                        MainActivity.mediaPlayer = FragSongs.mediaPlayer;
                    }
                    FragSongs.seekBar.setMax(FragSongs.mediaPlayer.getDuration());
                    FragSongs.seekBar.setProgress(0);
                    buildSeekBar(context);
                    FragSongs.mediaPlayer.start();
                    buildView(FragSongs.poss);
                    FragSongs.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                        @Override
                        public void onProgressChanged(final SeekBar seekBar, int i, boolean b) {


                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                            FragSongs.mediaPlayer.seekTo(seekBar.getProgress());

                        }
                    });

                }

        else if (intent.getAction().toString() == "back")
        {
            if (FragSongs.poss == null)
                FragSongs.poss = MainActivity.pos;

            if (FragSongs.poss < 0)
                FragSongs.poss = FragSongs.list.size()-1;
            else {
                --FragSongs.poss;
            }


            if (FragSongs.mediaPlayer != null)
                FragSongs.mediaPlayer.reset();
            Uri uri = Uri.parse(FragSongs.list.get(FragSongs.poss).getId());
            FragSongs.mediaPlayer = MediaPlayer.create(context, uri);
            if (MainActivity.mediaPlayer != null) {
                MainActivity.mediaPlayer.reset();
                MainActivity.mediaPlayer = FragSongs.mediaPlayer;
            }
            FragSongs.seekBar.setMax(FragSongs.mediaPlayer.getDuration());
            FragSongs.seekBar.setProgress(0);
            buildSeekBar(context);
            FragSongs.mediaPlayer.start();
            buildView(FragSongs.poss);
            FragSongs.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onProgressChanged(final SeekBar seekBar, int i, boolean b) {


                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    FragSongs.mediaPlayer.seekTo(seekBar.getProgress());

                }
            });
        }

    }
    private void buildSeekBar(final Context context) {
        long time;


        time = FragSongs.mediaPlayer.getDuration();
        final String t = milliSecondsToTimer(time);
        FragSongs.txt_end.post(new Runnable() {
            @Override
            public void run() {
                FragSongs.txt_end.setText(t);

            }
        });


        new Thread(new Runnable() {
            int currentPos = FragSongs.mediaPlayer.getCurrentPosition();


            @Override
            public void run() {
                while (currentPos <= FragSongs.seekBar.getMax()) {
                    SystemClock.sleep(500);

                    FragSongs.seekBar.setProgress(FragSongs.mediaPlayer.getCurrentPosition());
                    currentPos = FragSongs.mediaPlayer.getCurrentPosition();
                    FragSongs.seekBar.post(new Runnable() {
                        @Override
                        public void run() {
                            FragSongs.txt_start.setText(milliSecondsToTimer(FragSongs.mediaPlayer.getCurrentPosition()));
                        }
                    });
                }

//                if (isLoop) {
//                    if (FragSongs.poss == null)
//                        FragSongs.poss = MainActivity.pos;
//                    if (FragSongs.poss >= FragSongs.list.size() - 1)
//                        FragSongs.poss = 0;
//                    else {
//                        ++FragSongs.poss;
//                    }
//
//
//                    if (FragSongs.mediaPlayer != null)
//                        FragSongs.mediaPlayer.reset();
//                    Uri uri = Uri.parse(FragSongs.list.get(FragSongs.poss).getId());
//                    FragSongs.mediaPlayer = MediaPlayer.create(context, uri);
//                    if (MainActivity.mediaPlayer != null) {
//                        MainActivity.mediaPlayer.reset();
//                        MainActivity.mediaPlayer = FragSongs.mediaPlayer;
//                    }
//                    FragSongs.seekBar.setMax(FragSongs.mediaPlayer.getDuration());
//                    FragSongs.seekBar.setProgress(0);
//                    buildSeekBar(context);
//                    FragSongs.mediaPlayer.start();
//                    buildView(FragSongs.poss);
//                    FragSongs.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//
//                        @Override
//                        public void onProgressChanged(final SeekBar seekBar, int i, boolean b) {
//
//
//                        }
//
//                        @Override
//                        public void onStartTrackingTouch(SeekBar seekBar) {
//
//                        }
//
//                        @Override
//                        public void onStopTrackingTouch(SeekBar seekBar) {
//                            FragSongs.mediaPlayer.seekTo(seekBar.getProgress());
//
//                        }
//                    });
//                }


            }
        }).start();


    }
    public String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }
    private void buildView(final int pos) {


        FragSongs.txt_name.post(new Runnable() {
            @Override
            public void run() {

                FragSongs.txt_name.setText(FragSongs.list.get(pos).getName());

            }
        });
        FragSongs.txt_sub_name.post(new Runnable() {
            @Override
            public void run() {
                FragSongs.txt_sub_name.setText(FragSongs.list.get(pos).getArtist_id());

            }
        });
        FragSongs.btn_stop.setVisibility(View.VISIBLE);
        FragSongs.btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragSongs.btn_stop.setVisibility(View.GONE);
                FragSongs.btn_start.setVisibility(View.VISIBLE);

                FragSongs.mediaPlayer.pause();

            }
        });
        FragSongs.btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragSongs.btn_stop.setVisibility(View.GONE);
                FragSongs.btn_stop.setVisibility(View.VISIBLE);


                FragSongs.mediaPlayer.start();

            }
        });


    }

}
