package com.example.emojitest.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.emojitest.R;
import com.example.emojitest.model.Background;

import java.util.ArrayList;

public class BackgroundAdapter extends RecyclerView.Adapter<BackgroundAdapter.ViewHolder> {
    private Context context;
    ArrayList<Background> backgroundList = new ArrayList<>();
    private int selectedPosition = -1;

    BackgroundAdapter.iClickListener mClick;

    public interface iClickListener {
        void onClickItem(Background background);
    }

    public BackgroundAdapter(Context context,  BackgroundAdapter.iClickListener iClickListener,int selectedPosition) {
        this.context = context;
        this.mClick = iClickListener;
        this.selectedPosition = selectedPosition;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_bg_color, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Background background = backgroundList.get(position);
        if (selectedPosition == position) {
            Drawable customDrawable = context.getResources().getDrawable(R.drawable.bg_image_selected);
            holder.itemView.setBackgroundDrawable(customDrawable);
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
        if (background == null) {
            return;
        }
        Glide.with(context).load(background.getBg()).into(holder.img_bg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = holder.getAdapterPosition();
                mClick.onClickItem(background);
                SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor1 = preferences1.edit();
                editor1.putInt("select_background_text", selectedPosition);
                editor1.apply();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("select_bg_create_text", backgroundList.get(position).getBg());
                editor.apply();
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_bg = itemView.findViewById(R.id.img_bg);
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
