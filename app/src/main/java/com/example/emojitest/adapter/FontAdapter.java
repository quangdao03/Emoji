package com.example.emojitest.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.emojitest.R;
import com.example.emojitest.model.ObjFont;

import java.util.ArrayList;



public class FontAdapter extends RecyclerView.Adapter<FontAdapter.MyViewHolder> {

    ArrayList<ObjFont> objFont = new ArrayList<>();
    interfacetext interfaceText;
    Context context;

    private int selectedPosition = -1;

    public FontAdapter(Context mcontext, interfacetext interfaceText) {
        this.context = mcontext;
        this.interfaceText = interfaceText;
    }

    @NonNull
    @Override

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_font, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try {

            if (selectedPosition == position) {
                Drawable customDrawable = context.getResources().getDrawable(R.drawable.bg_image_selected);
                holder.txtTitle.setBackgroundDrawable(customDrawable);
                holder.txtTitle.setTextColor(Color.parseColor("#241912"));
            } else {
                holder.txtTitle.setTextColor(Color.parseColor("#9F8D83"));
                holder.txtTitle.setBackgroundColor(Color.WHITE);
            }

            holder.txtTitle.setText("Aa");
            holder.txtTitle.setTypeface(Typeface.createFromAsset(context.getAssets(), objFont.get(position).fontname));

            holder.txtTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPosition = holder.getAdapterPosition();
                    notifyDataSetChanged();
                    interfaceText.onClick(objFont.get(position).getFontname());
                }
            });
        } catch (Exception e) {
        }


    }

    @Override
    public int getItemCount() {
        return objFont.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle;

        public MyViewHolder(View view) {
            super(view);
            txtTitle = view.findViewById(R.id.txtTitle);

        }
    }

    public void addAll(ArrayList<ObjFont> data) {
        try {
            this.objFont.clear();
            this.objFont.addAll(data);
        } catch (Exception e) {
        }
        notifyDataSetChanged();
    }

    public interface interfacetext {

        void onClick(String fontpath);


    }
}

