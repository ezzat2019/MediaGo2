package com.example.programmer.mediago2;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Window;
import android.widget.Toast;

import com.example.programmer.mediago2.adapters.AdapterOfAritest;
import com.example.programmer.mediago2.adapters.AdapterOfAritestAlbum;
import com.example.programmer.mediago2.entity.AritistEntity;
import com.example.programmer.mediago2.fragments.FragSongs;
import com.example.programmer.mediago2.mp3Helper.MusicHelper;

import java.util.ArrayList;
import java.util.List;

public class MusicItemActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<AritistEntity> aritistEntities;
    AdapterOfAritestAlbum adapterOfAlbumst;
    private MusicHelper musicHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_items);

        buildEnterAnimation();
        buildRec();

    }

    private void buildRec() {
        musicHelper=MusicHelper.getInstance(getApplicationContext());
        aritistEntities=new ArrayList<>();


        aritistEntities=musicHelper.getAllArtistt();

        recyclerView=findViewById(R.id.rec_items);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));


        adapterOfAlbumst=new AdapterOfAritestAlbum(this,aritistEntities);
        adapterOfAlbumst.setOnItem(new AdapterOfAritestAlbum.OnItemClick() {
            @Override
            public void onClick(int pos) {
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("song","song");
                startActivity(intent);


            }
        });
        recyclerView.setAdapter(adapterOfAlbumst);

    }

    private void buildEnterAnimation() {
        Slide explode=new Slide();
        explode.setSlideEdge(Gravity.RIGHT);

        explode.setDuration(700);
        getWindow().setEnterTransition(new Explode().setDuration(600));

    }
}
