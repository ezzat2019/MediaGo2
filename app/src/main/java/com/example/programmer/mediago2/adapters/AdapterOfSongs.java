package com.example.programmer.mediago2.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.programmer.mediago2.R;
import com.example.programmer.mediago2.entity.MediaEntity;

import java.util.List;

public class AdapterOfSongs extends RecyclerView.Adapter<AdapterOfSongs.VH> {
    private List<MediaEntity> list;
    private Context context;
    protected  onItemClickLissteners lissteners;

    public interface onItemClickLissteners
    {
        void onClick(int pos,View view);
    }

    public  void onItemSelected(onItemClickLissteners lissteners)
    {
        this.lissteners=lissteners;
    }

    public AdapterOfSongs(List<MediaEntity> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_song, viewGroup, false);
        return new VH(view,lissteners);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        vh.txt_name.setText(list.get(i).getName());
        vh.txt_name_artist.setText(list.get(i).getArtist_id());
        vh.txt_duration.setText(list.get(i).getDuration());
        vh.setIsRecyclable(false);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        private TextView txt_name, txt_name_artist,txt_duration;
        private LinearLayout linearLayout;

        public VH(@NonNull final View itemView, final onItemClickLissteners lissteners) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.song_name);
            txt_name.setSelected(true);
            txt_name_artist = itemView.findViewById(R.id.song_name_artist);
            txt_name_artist.setSelected(true);
            linearLayout=itemView.findViewById(R.id.linn);
            txt_duration=itemView.findViewById(R.id.song_duration);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lissteners.onClick(getPosition(),view);

                    linearLayout.setBackgroundColor(Color.argb(30,100,30,160));





                }
            });
        }
    }
}
