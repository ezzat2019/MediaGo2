package com.example.programmer.mediago2.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.programmer.mediago2.MainActivity;
import com.example.programmer.mediago2.R;
import com.example.programmer.mediago2.adapters.AdapterOfSongs;
import com.example.programmer.mediago2.entity.MediaEntity;
import com.example.programmer.mediago2.mp3Helper.MusicHelper;
import com.example.programmer.mediago2.services.MyService;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragSongs extends Fragment {
    private RecyclerView recyclerView;
    private MusicHelper helper;
    public AdapterOfSongs adapter;
    public static Integer poss;
    private String name;
    public static List<MediaEntity> list;
    protected boolean isLoop = false;
    public static SeekBar seekBar;
    public static MediaPlayer mediaPlayer;
    private View view;
    private BottomSheetBehavior bottomSheetBehavior;
    public  static ImageView btn_start, btn_back, btn_next, btn_stop, btn_loop, btn_loop_on;
    public static TextView txt_name, txt_sub_name, txt_start, txt_end;


    public FragSongs() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public FragSongs(View view) {


        this.view = view;


    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = MusicHelper.getInstance(getContext().getApplicationContext());
        list = new ArrayList<>();
        list = helper.getAllMedia();
        bottomSheetBehavior = BottomSheetBehavior.from(view);
        btn_start = view.findViewById(R.id.img_start);
        btn_stop = view.findViewById(R.id.imag_stop);
        btn_next = view.findViewById(R.id.imag_next);
        btn_loop = view.findViewById(R.id.btn_loop);

        btn_loop_on = view.findViewById(R.id.btn_loop_on);
        btn_back = view.findViewById(R.id.ima_back);
        txt_name = view.findViewById(R.id.txt);
        seekBar = view.findViewById(R.id.seekBar);

        txt_start = view.findViewById(R.id.txt_start);
        txt_end = view.findViewById(R.id.txt_end);
        txt_sub_name = view.findViewById(R.id.play_name_artist);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag_songs, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView = view.findViewById(R.id.rec_songs);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
        adapter = new AdapterOfSongs(list, getContext().getApplicationContext());
        txt_name.setSelected(true);



    }

    private void createNotification(String  s) {
        Intent intent=new Intent(getContext().getApplicationContext(), MyService.class);
        intent.putExtra("name",s);

        ContextCompat.startForegroundService(getContext(),intent);



    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onStart() {
        super.onStart();

        buildBBtnMusic();
        adapter.onItemSelected(new AdapterOfSongs.onItemClickLissteners() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(int pos, View v) {
                poss = pos;


                SharedPreferences sharedPreferences = getContext().getSharedPreferences("ezzat", Context.MODE_PRIVATE);
                SharedPreferences.Editor preferences = sharedPreferences.edit();
                preferences.putInt("pos", poss);
                preferences.commit();


                if (mediaPlayer != null) {
                    mediaPlayer.reset();

                    Uri uri = Uri.parse(list.get(pos).getId());
                    mediaPlayer = MediaPlayer.create(getContext().getApplicationContext(), uri);


                }
                if (MainActivity.mediaPlayer != null) {


                    MainActivity.mediaPlayer.reset();
                    Uri uri = Uri.parse(list.get(pos).getId());
                    mediaPlayer = MediaPlayer.create(getContext().getApplicationContext(), uri);
                    MainActivity.mediaPlayer = mediaPlayer;


                }

                if (mediaPlayer == null) {
                    Uri uri = Uri.parse(list.get(pos).getId());
                    mediaPlayer = MediaPlayer.create(getContext().getApplicationContext(), uri);
                }

                seekBar.setMax(mediaPlayer.getDuration());

                buildSeekBar();
                mediaPlayer.start();


                buildView(pos);
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(final SeekBar seekBar, int i, boolean b) {


                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        mediaPlayer.seekTo(seekBar.getProgress());

                    }
                });
            }
        });
        recyclerView.setAdapter(adapter);


        getShared();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getShared() {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("ezzat", Context.MODE_PRIVATE);
        if (sharedPreferences != null   ) {
            poss = sharedPreferences.getInt("pos", 0);


            if (mediaPlayer != null) {
                mediaPlayer.reset();

                Uri uri = Uri.parse(list.get(poss).getId());
                mediaPlayer = MediaPlayer.create(getContext().getApplicationContext(), uri);


            }
            if (MainActivity.mediaPlayer != null) {


                MainActivity.mediaPlayer.reset();
                Uri uri = Uri.parse(list.get(poss).getId());
                mediaPlayer = MediaPlayer.create(getContext().getApplicationContext(), uri);
                MainActivity.mediaPlayer = mediaPlayer;


            }

            if (mediaPlayer == null) {
                Uri uri = Uri.parse(list.get(poss).getId());
                mediaPlayer = MediaPlayer.create(getContext().getApplicationContext(), uri);
            }

            seekBar.setMax(mediaPlayer.getDuration());

            buildSeekBar();




            txt_name.post(new Runnable() {
                @Override
                public void run() {
                    txt_name.setText(list.get(poss).getName());

                }
            });
            txt_sub_name.post(new Runnable() {
                @Override
                public void run() {
                    txt_sub_name.setText(list.get(poss).getArtist_id());

                }
            });
            btn_start.setVisibility(View.VISIBLE);
            btn_stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mediaPlayer.isPlaying()) {
                        btn_stop.setVisibility(View.GONE);
                        btn_start.setVisibility(View.VISIBLE);

                        mediaPlayer.pause();
                    }
                }
            });
            btn_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!mediaPlayer.isPlaying()) {
                        btn_stop.setVisibility(View.GONE);
                        btn_stop.setVisibility(View.VISIBLE);

                        mediaPlayer.start();
                    }
                }
            });
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onProgressChanged(final SeekBar seekBar, int i, boolean b) {


                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    mediaPlayer.seekTo(seekBar.getProgress());

                }
            });
        }
    }

    private void buildBBtnMusic() {
        btn_loop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isLoop = true;
                btn_loop.setVisibility(View.INVISIBLE);

                btn_loop_on.setVisibility(View.VISIBLE);


            }
        });
        btn_loop_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                isLoop = false;
                btn_loop.setVisibility(View.VISIBLE);

                btn_loop_on.setVisibility(View.INVISIBLE);
//
            }
        });


        btn_next.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if (poss == null)
                    poss = MainActivity.pos;

                if (poss >= list.size() - 1)
                    poss = 0;
                else {
                    ++poss;
                }


                if (mediaPlayer != null)
                    mediaPlayer.reset();
                Uri uri = Uri.parse(list.get(poss).getId());
                mediaPlayer = MediaPlayer.create(getContext(), uri);
                if (MainActivity.mediaPlayer != null) {
                    MainActivity.mediaPlayer.reset();
                    MainActivity.mediaPlayer = mediaPlayer;
                }
                seekBar.setMax(mediaPlayer.getDuration());
                seekBar.setProgress(0);
                buildSeekBar();
                mediaPlayer.start();
                buildView(poss);
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(final SeekBar seekBar, int i, boolean b) {


                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        mediaPlayer.seekTo(seekBar.getProgress());

                    }
                });

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if (poss == null)
                    poss = MainActivity.pos;

                if (poss <= 0)
                    poss = list.size() - 1;
                else {
                    --poss;
                }
                if (mediaPlayer != null)
                    mediaPlayer.reset();
                Uri uri = Uri.parse(list.get(poss).getId());
                mediaPlayer = MediaPlayer.create(getContext(), uri);
                if (MainActivity.mediaPlayer != null) {
                    MainActivity.mediaPlayer.reset();
                    MainActivity.mediaPlayer = mediaPlayer;
                }
                seekBar.setMax(mediaPlayer.getDuration());
                seekBar.setProgress(0);
                buildSeekBar();

                mediaPlayer.start();
                buildView(poss);
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(final SeekBar seekBar, int i, boolean b) {


                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        mediaPlayer.seekTo(seekBar.getProgress());

                    }
                });


            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void buildSeekBar() {
        long time;


        time = mediaPlayer.getDuration();
        final String t = milliSecondsToTimer(time);
        txt_end.post(new Runnable() {
            @Override
            public void run() {
                txt_end.setText(t);

            }
        });


        new Thread(new Runnable() {
            int currentPos = mediaPlayer.getCurrentPosition();


            @Override
            public void run() {
                while (currentPos <= seekBar.getMax()) {
                    SystemClock.sleep(500);

                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    currentPos = mediaPlayer.getCurrentPosition();
                    seekBar.post(new Runnable() {
                        @Override
                        public void run() {
                            txt_start.setText(milliSecondsToTimer(mediaPlayer.getCurrentPosition()));
                        }
                    });
                }

                if (isLoop) {
                    if (poss == null)
                        poss = MainActivity.pos;
                    if (poss >= list.size() - 1)
                        poss = 0;
                    else {
                        ++poss;
                    }


                    if (mediaPlayer != null)
                        mediaPlayer.reset();
                    Uri uri = Uri.parse(list.get(poss).getId());
                    mediaPlayer = MediaPlayer.create(getContext(), uri);
                    if (MainActivity.mediaPlayer != null) {
                        MainActivity.mediaPlayer.reset();
                        MainActivity.mediaPlayer = mediaPlayer;
                    }
                    seekBar.setMax(mediaPlayer.getDuration());
                    seekBar.setProgress(0);
                    buildSeekBar();
                    mediaPlayer.start();
                    buildView(poss);
                    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                        @Override
                        public void onProgressChanged(final SeekBar seekBar, int i, boolean b) {


                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                            mediaPlayer.seekTo(seekBar.getProgress());

                        }
                    });
                }


            }
        }).start();


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

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


        txt_name.post(new Runnable() {
            @Override
            public void run() {
               name=list.get(pos).getName();
                txt_name.setText(name);
                if (name!=null)
                    createNotification(name);

            }
        });
        txt_sub_name.post(new Runnable() {
            @Override
            public void run() {
                txt_sub_name.setText(list.get(pos).getArtist_id());

            }
        });
        btn_stop.setVisibility(View.VISIBLE);
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_stop.setVisibility(View.GONE);
                btn_start.setVisibility(View.VISIBLE);

                mediaPlayer.pause();
                stopNotification();
            }
        });
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_stop.setVisibility(View.GONE);
                btn_stop.setVisibility(View.VISIBLE);


                mediaPlayer.start();
                if (name!=null)
                createNotification(name);
            }
        });


    }
   void stopNotification(){
       Intent intent=new Intent(getContext().getApplicationContext(), MyService.class);
       getActivity().stopService(intent);
   }


}
