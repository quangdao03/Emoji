package com.example.emojitest.adapter;




import static com.example.emojitest.activity.MyCreationScreenActivity.back;
import static com.example.emojitest.activity.MyCreationScreenActivity.cancel;
import static com.example.emojitest.activity.MyCreationScreenActivity.clear_item;
import static com.example.emojitest.activity.MyCreationScreenActivity.ll_delete;
import static com.example.emojitest.activity.MyCreationScreenActivity.ll_select;
import static com.example.emojitest.activity.MyCreationScreenActivity.select_all;
import static com.example.emojitest.activity.MyCreationScreenActivity.unselect_all;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.emojitest.R;
import com.example.emojitest.activity.MyCreationScreenActivity;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class MyCreationAdapterMyGallery extends RecyclerView.Adapter<MyCreationAdapterMyGallery.ViewHolderMyCreation> {
    List<Uri> listImageSaved = new ArrayList<>();
    private int selectedCount = 0;
    Context context;
    OnClickImage onClickImage;

    boolean[] selected;
    public boolean isSelectionMode = false;

    public static String path = "";

    private HashSet<Uri> selectedUris = new HashSet<>();

    public MyCreationAdapterMyGallery(Context context, List<Uri> listImageSaved, OnClickImage onClickImage) {
        this.context = context;
        this.listImageSaved = listImageSaved;
        this.onClickImage = onClickImage;
        selected = new boolean[listImageSaved.size()];
        Arrays.fill(selected, false);
    }

    @NonNull
    @Override
    public ViewHolderMyCreation onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_creation1, parent, false);
        return new ViewHolderMyCreation(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMyCreation holder, @SuppressLint("RecyclerView") int position) {
        Uri uri = listImageSaved.get(position);
        if (selected[position]) {
            holder.itemImg1.setVisibility(View.VISIBLE);
            selectedCount++;
        } else {
            holder.itemImg1.setVisibility(View.GONE);
            selectedCount--;
        }
        Glide.with(this.context).load(uri.toString()).thumbnail(0.1f).dontAnimate().centerCrop().placeholder(R.drawable.imgpsh_fullsize_anim).error(R.drawable.imgpsh_fullsize_anim).into(holder.imgAlbum);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                toggleSelection(position);
                isSelectionMode = true;
                if (getSelectedCount() >= 1) {
                    ll_select.setVisibility(View.VISIBLE);
                    ll_delete.setVisibility(View.VISIBLE);
                    ((MyCreationScreenActivity) context).showCancel();
                    select_all.setVisibility(View.VISIBLE);
                    clear_item.setVisibility(View.VISIBLE);
                } else {
                    cancel.setVisibility(View.GONE);
                    ll_delete.setVisibility(View.GONE);
                    ll_select.setVisibility(View.GONE);
                    select_all.setVisibility(View.VISIBLE);
                    clear_item.setVisibility(View.GONE);
                    back.setVisibility(View.VISIBLE);
                    unselect_all.setVisibility(View.GONE);
                }
                if (getSelectedCount() == listImageSaved.size()) {
                    unselect_all.setVisibility(View.VISIBLE);
                    select_all.setVisibility(View.GONE);
                }

                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSelectionMode == true) {
                    toggleSelection(position);
                    if (getSelectedCount() >= 1) {
                        ll_select.setVisibility(View.VISIBLE);
                        ll_delete.setVisibility(View.VISIBLE);
                        ((MyCreationScreenActivity) context).showCancel();
                        select_all.setVisibility(View.VISIBLE);
                        clear_item.setVisibility(View.VISIBLE);
                    } else {
                        cancel.setVisibility(View.GONE);
                        ll_select.setVisibility(View.GONE);
                        ll_delete.setVisibility(View.GONE);
                        back.setVisibility(View.VISIBLE);
                        select_all.setVisibility(View.VISIBLE);
                        clear_item.setVisibility(View.GONE);
                        unselect_all.setVisibility(View.GONE);
                        isSelectionMode = false;
                    }
                    if (getSelectedCount() == listImageSaved.size()) {
                        unselect_all.setVisibility(View.VISIBLE);
                        select_all.setVisibility(View.GONE);
                    }
                } else {
                    onClickImage.onClickImage(position);
                }
            }
        });

    }

    public void selectAll() {
        for (int i = 0; i < listImageSaved.size(); i++) {
            selected[i] = true;
            selectedUris.add(listImageSaved.get(i));
            notifyItemChanged(i);
            ;
        }
    }

    public void clearSelection() {
        for (int i = 0; i < listImageSaved.size(); i++) {
            selected[i] = false;
            selectedUris.clear();
            notifyItemChanged(i);
            isSelectionMode = false;
        }
    }

    public void clearSelection1() {
        for (int i = 0; i < listImageSaved.size(); i++) {
            selected[i] = false;
            selectedUris.clear();
            notifyItemChanged(i);
            isSelectionMode = true;
        }
    }

    public void toggleSelection(int position) {
        selected[position] = !selected[position];
        Uri uri = listImageSaved.get(position);
        if (selected[position]) {
            selectedUris.add(uri);
        } else {
            unselect_all.setVisibility(View.GONE);
            selectedUris.remove(uri);

        }
        notifyItemChanged(position);
    }

    public void removeSelectedItems() {
        listImageSaved.removeAll(selectedUris);
        notifyDataSetChanged();
        clearSelection();
        isSelectionMode = false;
        selectedUris.clear();
    }

    public int getSelectedCount() {
        return selectedUris.size();
    }

    public ArrayList<Uri> getSelectedUris() {
        ArrayList<Uri> selectedUris = new ArrayList<>(this.selectedUris);
        for (Uri uri : selectedUris) {
            path = uri.getPath();
        }
        return selectedUris;
    }

    @Override
    public int getItemCount() {
        return listImageSaved.size();
    }


    public class ViewHolderMyCreation extends RecyclerView.ViewHolder {
        ImageView imgAlbum, itemImg1;


        public ViewHolderMyCreation(@NonNull View itemView) {
            super(itemView);
            imgAlbum = itemView.findViewById(R.id.itemImg);
            itemImg1 = itemView.findViewById(R.id.itemImg1);
        }
    }

    public interface OnClickImage {
        void onClickImage(int pos);
    }
}
