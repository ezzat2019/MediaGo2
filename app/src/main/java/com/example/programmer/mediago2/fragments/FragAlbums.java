package com.example.programmer.mediago2.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.programmer.mediago2.MainActivity;
import com.example.programmer.mediago2.R;
import com.example.programmer.mediago2.adapters.AdapterOfAlbum;
import com.example.programmer.mediago2.entity.AlbumEntity;
import com.example.programmer.mediago2.mp3Helper.MusicHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragAlbums extends Fragment {
    private AdapterOfAlbum adapter;
    private List<AlbumEntity>list;
    private MusicHelper helper;
    private RecyclerView recyclerView;


    public FragAlbums() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper=MusicHelper.getInstance(getContext().getApplicationContext());
        list=new ArrayList<>();
        list=helper.getAllAlbums();




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag_albums, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.rec_album);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext().getApplicationContext()
        ,2));
        adapter=new AdapterOfAlbum(getContext().getApplicationContext(),list);
        recyclerView.setAdapter(adapter);
        adapter.setOnClick(new AdapterOfAlbum.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                Toast.makeText(getContext(), pos+" for test ", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
