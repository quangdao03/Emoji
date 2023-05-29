package com.example.emojitest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.emojitest.R;
import com.example.emojitest.model.Background;

import java.util.List;

public class BackgroundAdapter extends RecyclerView.Adapter<BackgroundAdapter.ViewHolder> {
    private Context context;
    List<Background> backgroundList;

    public BackgroundAdapter(Context context, List<Background> backgroundList) {
        this.context = context;
        this.backgroundList = backgroundList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_bg, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Background background = backgroundList.get(position);
        if (background == null) {
            return;
        }
        Glide.with(context).load(background.getBg()).into(holder.img_bg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return backgroundList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_bg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_bg = itemView.findViewById(R.id.img_bg);
        }
    }
}
