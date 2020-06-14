package com.example.programmer.mediago2.adapters;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.programmer.mediago2.MusicItemActivity;
import com.example.programmer.mediago2.R;
import com.example.programmer.mediago2.entity.AritistEntity;

import java.util.List;

public class AdapterOfAritest extends RecyclerView.Adapter<AdapterOfAritest.VH> {

   protected static Context context;
    private List<AritistEntity> playLists;
    private OnItemClick itemClick;

    public   interface OnItemClick
    {
        void onClick(int pos);
    }
    public void setOnItem(OnItemClick click)
    {
        this.itemClick=click;
    }

    public AdapterOfAritest(Context context, List<AritistEntity> playLists) {
        this.context = context;
        this.playLists = playLists;

    }

    @NonNull
    @Override
    public AdapterOfAritest.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {




        View view= LayoutInflater.from(context).inflate(R.layout.item_artist,viewGroup,false);
        return new AdapterOfAritest.VH(view,itemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOfAritest.VH vh, int i) {
        vh.title.setText(playLists.get(i).getName());

    }

    @Override
    public int getItemCount() {
        return playLists.size();
    }

    static class VH extends RecyclerView.ViewHolder
    {
        private TextView title;
        public VH(@NonNull View itemView,final OnItemClick click) {
            super(itemView);
            title=itemView.findViewById(R.id.artist_sub_title);
            title.setSelected(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    click.onClick(getPosition());


                }
            });

        }
    }
}
