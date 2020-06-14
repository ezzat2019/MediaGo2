package com.example.programmer.mediago2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.programmer.mediago2.entity.MediaEntity;
import com.example.programmer.mediago2.fragments.FragAlbums;
import com.example.programmer.mediago2.fragments.FragAritist;
import com.example.programmer.mediago2.fragments.FragSongs;
import com.example.programmer.mediago2.mp3Helper.MusicHelper;
import com.example.programmer.mediago2.services.MyService;
import com.example.programmer.mediago2.viewpagers.MyViewPager;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static ViewPager viewPager;
    private ImageView imageView;
    private String name;

    private TextView textView;
    private boolean isExit = true;
    private TabLayout tabLayout;
    private MaterialSearchView searchView;
    private MyViewPager myViewPager;
    private List<Fragment> fragmentList;
    private boolean backed = false;
    public static int pos;
    private static List<MediaEntity> list;
    private Map<String, Integer> list2;
    private FragSongs songs;
    protected boolean isLoop = false;
    private SeekBar seekBar;
    View view;
    public static MediaPlayer mediaPlayer;
    private BottomSheetBehavior bottomSheetBehavior;
    private ImageView btn_start, btn_back, btn_next, btn_stop, btn_loop, btn_loop_on;
    private TextView txt_name, txt_sub_name, txt_start, txt_end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buildEnterAnimation();

        view = findViewById(R.id.bottom_sheet);
        songs = new FragSongs(view);


        buildAllView();


        buildBttomSheet();


        buildToolBar();

        buildSearchView();


        if (getIntent().getStringExtra("song") != null)
            viewPager.setCurrentItem(2);


    }


    private void buildEnterAnimation() {
        Slide explode = new Slide();
        explode.setSlideEdge(Gravity.BOTTOM);

        getWindow().setEnterTransition(new Fade().setDuration(500));

    }


    private void buildAllView() {
        imageView = findViewById(R.id.imageView2);
        textView = findViewById(R.id.txt);
        textView.setSelected(true);

        btn_start = findViewById(R.id.img_start);
        btn_stop = findViewById(R.id.imag_stop);
        btn_next = findViewById(R.id.imag_next);
        btn_loop = findViewById(R.id.btn_loop);

        btn_loop_on = findViewById(R.id.btn_loop_on);
        btn_back = findViewById(R.id.ima_back);
        txt_name = findViewById(R.id.txt);

        txt_start = findViewById(R.id.txt_start);
        txt_end = findViewById(R.id.txt_end);
        txt_sub_name = findViewById(R.id.play_name_artist);
        seekBar = view.findViewById(R.id.seekBar);
        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));
    }

    private void buildSearchView() {

        buildBBtnMusic();
        searchView = findViewById(R.id.search_view);
        searchView.setCursorDrawable(R.drawable.shape);
        list = new ArrayList<>();
        list2 = new HashMap<>();

        list = MusicHelper.getInstance(getApplicationContext()).getAllMedia();


        final String[] str1 = new String[list.size()];

        for (int i = 0; i < list.size(); i++) {

            str1[i] = list.get(i).getName();
            list2.put(list.get(i).getName(), i);
        }
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!list2.containsKey(query)) {
                    Toast.makeText(MainActivity.this, "sorry! not found try again ", Toast.LENGTH_SHORT).show();

                } else {
                    getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                    );
                    searchView.closeSearch();
                    pos = list2.get(query);
                    start(pos);

                }


                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });


        searchView.setSuggestions(str1);
    }

    private void createNotification() {
        Intent intent = new Intent(getApplicationContext(), MyService.class);
        ContextCompat.startForegroundService(this, intent);


    }

    @Override
    protected void onDestroy() {
        stopNotification();
        FragSongs.mediaPlayer.stop();
        super.onDestroy();

    }

    private void createNotification(String s) {
        Intent intent = new Intent(getApplicationContext(), MyService.class);
        intent.putExtra("name", s);

        ContextCompat.startForegroundService(getApplicationContext(), intent);


    }


    public void stopNotification() {
        Intent intent = new Intent(getApplicationContext(), MyService.class);
        stopService(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void start(int pos) {


        if (mediaPlayer != null) {

            mediaPlayer.reset();
            Uri uri = Uri.parse(list.get(pos).getId());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);

        }
        if (FragSongs.mediaPlayer != null) {

            FragSongs.mediaPlayer.reset();
            Uri uri = Uri.parse(list.get(pos).getId());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            FragSongs.mediaPlayer = mediaPlayer;

        }

        if (mediaPlayer == null) {
            Uri uri = Uri.parse(list.get(pos).getId());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
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

            }
        });


        btn_next.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if (pos >= list.size() - 1)
                    pos = 0;
                else {
                    ++pos;
                }


                if (mediaPlayer != null)
                    mediaPlayer.reset();
                Uri uri = Uri.parse(list.get(pos).getId());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);

                seekBar.setMax(mediaPlayer.getDuration());
                seekBar.setProgress(0);
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

        btn_back.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if (pos <= 0)
                    pos = list.size() - 1;
                else {
                    --pos;
                }


                if (mediaPlayer != null)
                    mediaPlayer.reset();
                Uri uri = Uri.parse(list.get(pos).getId());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);

                seekBar.setMax(mediaPlayer.getDuration());
                seekBar.setProgress(0);
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
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void buildSeekBar() {
        long time = mediaPlayer.getDuration();
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
                    if (pos >= list.size() - 1)
                        pos = 0;
                    else {
                        ++pos;
                    }


                    if (mediaPlayer != null)
                        mediaPlayer.reset();
                    Uri uri = Uri.parse(list.get(pos).getId());
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);

                    seekBar.setMax(mediaPlayer.getDuration());
                    seekBar.setProgress(0);
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


        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        txt_name.post(new Runnable() {
            @Override
            public void run() {
                name = list.get(pos).getName();
                txt_name.setText(name);
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
                if (mediaPlayer.isPlaying())
                    mediaPlayer.pause();
                stopNotification();
            }
        });
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_stop.setVisibility(View.GONE);
                btn_stop.setVisibility(View.VISIBLE);
                if (!mediaPlayer.isPlaying())
                    mediaPlayer.start();
                createNotification(name);
            }
        });


    }

    private void buildBttomSheet() {

        RelativeLayout relativeLayout = findViewById(R.id.bottom_sheet);

        bottomSheetBehavior = BottomSheetBehavior.from(relativeLayout);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });


    }


    private void buildToolBar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        viewPager = findViewById(R.id.main_view_pager);
        tabLayout = findViewById(R.id.main_tab);

        initPagerAndTabLayout();

    }


    private void initPagerAndTabLayout() {

        fragmentList = new ArrayList<>();
        fragmentList.add(songs);

        fragmentList.add(new FragAritist());
        fragmentList.add(new FragAlbums());


        myViewPager = new MyViewPager(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(myViewPager);
        tabLayout.setupWithViewPager(viewPager);


    }



    @Override
    public void onBackPressed() {
        if (bottomSheetBehavior.getState() == bottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            return;
        }

        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
            return;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            } else {
                if (!backed) {
                    Toast.makeText(this, "press  again to exit", Toast.LENGTH_SHORT).show();
                    backed = true;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SystemClock.sleep(2000);
                            backed = false;

                        }
                    }).start();

                } else {

                    super.onBackPressed();

                }


            }

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_music) {
            // Handle the camera action
        } else if (id == R.id.nav_recent) {

        } else if (id == R.id.nav_music_lib) {

        } else if (id == R.id.nav_setting) {

        } else if (id == R.id.nav_help) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
