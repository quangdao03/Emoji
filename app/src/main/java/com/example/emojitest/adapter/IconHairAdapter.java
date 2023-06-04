package com.example.emojitest.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.emojitest.R;
import com.example.emojitest.model.Icon;

import java.util.ArrayList;

public class IconHairAdapter extends RecyclerView.Adapter<IconHairAdapter.ViewHolder> {
    Context context;
    ArrayList<Icon> iconList = new ArrayList<>();
    iClickListener mClick;
    private int selectedPosition = -1;

    public interface iClickListener {
        void onClickItem(Icon icon);
    }

    public IconHairAdapter(Context context, iClickListener iClickListener, int selectedPosition) {
        this.context = context;
        this.mClick = iClickListener;
        this.selectedPosition = selectedPosition;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout_icon, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Icon icon = iconList.get(position);
        if (selectedPosition == position) {
            Drawable customDrawable = context.getResources().getDrawable(R.drawable.bg_image_selected);
            holder.itemView.setBackgroundDrawable(customDrawable);
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
        Glide.with(context).load(Uri.parse(iconList.get(position).getStickerpath())).into(holder.image_icon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = holder.getAdapterPosition();
                notifyDataSetChanged();
                mClick.onClickItem(icon);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("selected_position_hair", selectedPosition);
                editor.apply();
            }
        });
    }

    @Override
    public int getItemCount() {
        return iconList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image_icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_icon = itemView.findViewById(R.id.image_icon);
        }
    }
    public void addAll(ArrayList<Icon> data) {
        try {
            this.iconList.clear();
            this.iconList.addAll(data);
        } catch (Exception e) {
        }
        notifyDataSetChanged();
    }
}
