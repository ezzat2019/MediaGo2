package com.example.programmer.mediago2.fragments;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.programmer.mediago2.MusicItemActivity;
import com.example.programmer.mediago2.R;
import com.example.programmer.mediago2.adapters.AdapterOfAritest;
import com.example.programmer.mediago2.entity.AritistEntity;
import com.example.programmer.mediago2.entity.ItemPlayList;
import com.example.programmer.mediago2.mp3Helper.MusicHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragAritist extends Fragment {
    private RecyclerView recyclerView;
    private List<AritistEntity> aritistEntities;
    AdapterOfAritest adapterOfAlbumst;
    private MusicHelper musicHelper;



    public FragAritist() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        musicHelper=MusicHelper.getInstance(getContext().getApplicationContext());
        aritistEntities=new ArrayList<>();


      aritistEntities=musicHelper.getAllArtistt();
      musicHelper.getArt();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag_aritist, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.rec_artist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),2));


        adapterOfAlbumst=new AdapterOfAritest(view.getContext(),aritistEntities);
        adapterOfAlbumst.setOnItem(new AdapterOfAritest.OnItemClick() {
            @Override
            public void onClick(int pos) {
                ActivityOptions options= (ActivityOptions) ActivityOptions.makeSceneTransitionAnimation(getActivity());
                startActivity(new Intent(getContext().getApplicationContext(), MusicItemActivity.class),options.toBundle());

            }
        });
        recyclerView.setAdapter(adapterOfAlbumst);






    }

}
