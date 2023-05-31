package com.example.emojitest.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.emojitest.R;
import com.example.emojitest.model.ObjColorCodeNeon;

import java.util.ArrayList;

public class ColorCodeAdapter extends RecyclerView.Adapter<ColorCodeAdapter.MyViewHolder> {

    ArrayList<ObjColorCodeNeon> objColorCodes = new ArrayList<>();
    Context context;
    Interfacecolor interfacecolor;
    private int selectedPosition = -1;

    public ColorCodeAdapter(Context context, Interfacecolor interfacecolor) {
        this.context = context;
        this.interfacecolor = interfacecolor;
        this.selectedPosition = selectedPosition;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_color_code, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try {
//            if (selectedPosition == position) {
//                holder.txtcolor1.setVisibility(View.VISIBLE);
//            } else {
//                holder.txtcolor.setBackgroundColor(Color.parseColor(objColorCodes.get(position).strColor));
//                holder.txtcolor1.setVisibility(View.GONE);
//            }


            holder.txtcolor.setBackgroundColor(Color.parseColor(objColorCodes.get(position).strColor));

            holder.txtcolor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedPosition = holder.getAdapterPosition();
                    notifyDataSetChanged();
                    interfacecolor.onClick(objColorCodes.get(position).strColor);
//                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//                    SharedPreferences.Editor editor = preferences.edit();
//                    editor.putInt("selected_position", selectedPosition);
//                    editor.apply();
                }
            });

        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return objColorCodes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout txtcolor;


        public MyViewHolder(View view) {
            super(view);

            txtcolor = view.findViewById(R.id.txtcolor);

        }
    }

    public void addAll(ArrayList<ObjColorCodeNeon> data) {
        try {
            this.objColorCodes.clear();
            this.objColorCodes.addAll(data);
        } catch (Exception e) {
        }
        notifyDataSetChanged();
    }

    public interface Interfacecolor {
        void onClick(String colorpath);
    }
}
