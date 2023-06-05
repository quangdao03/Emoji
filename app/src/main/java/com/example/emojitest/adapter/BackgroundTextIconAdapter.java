package com.example.emojitest.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.emojitest.R;
import com.example.emojitest.model.Background;

import java.util.ArrayList;

public class BackgroundTextIconAdapter extends RecyclerView.Adapter<BackgroundTextIconAdapter.ViewHolder> {
    private Context context;
    ArrayList<Background> backgroundList = new ArrayList<>();
    private int selectedPosition = -1;
    String selectedPositiona;

    BackgroundTextIconAdapter.iClickListener mClick;

    public interface iClickListener {
        void onClickItem(Background background);
    }

    public BackgroundTextIconAdapter(Context context, BackgroundTextIconAdapter.iClickListener iClickListener) {
        this.context = context;
        this.mClick = iClickListener;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        selectedPositiona = preferences.getString("select_bg_create_text", "file:///android_asset/backgroundcolor/icon1.png");


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_bg, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Background background = backgroundList.get(position);
        Glide.with(context).load(Uri.parse(selectedPositiona)).into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                holder.rl_image.setBackground(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });
        if (background == null) {
            return;
        }
        Glide.with(context).load(background.getBg()).into(holder.img_bg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = holder.getAdapterPosition();
                mClick.onClickItem(background);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return backgroundList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_bg;
        RelativeLayout rl_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_bg = itemView.findViewById(R.id.img_bg);
            rl_image = itemView.findViewById(R.id.rl_image);
        }
    }
    public void addAll(ArrayList<Background> data) {
        try {
            this.backgroundList.clear();
            this.backgroundList.addAll(data);
        } catch (Exception e) {
        }
        notifyDataSetChanged();
    }
}
