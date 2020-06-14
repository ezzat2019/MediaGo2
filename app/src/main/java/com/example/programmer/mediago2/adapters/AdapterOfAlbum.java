package com.example.programmer.mediago2.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.programmer.mediago2.R;

import java.util.List;

public class AdapterOfAlbum extends RecyclerView.Adapter<AdapterOfAlbum.VH> {
    private Context context;
    private List list;
    private OnItemClickListener listener;

   public interface OnItemClickListener {
        void onClick(int pos);
    }


    public AdapterOfAlbum(Context context, List list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_album, viewGroup, false);
        return new VH(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        vh.txtName.setText(list.get(i).toString());

    }

    public void setOnClick(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView txtName;

        public VH(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            txtName = itemView.findViewById(R.id.album_sub_title);
            txtName.setSelected(true);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(getPosition());
                }
            });

        }
    }
}
