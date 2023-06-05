package com.example.emojitest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.emojitest.Language.Interface.IClickItemLanguage;
import com.example.emojitest.Language.Model.LanguageModel;
import com.example.emojitest.R;


import java.util.List;

public class LanguageStartAdapter extends RecyclerView.Adapter<LanguageStartAdapter.LangugeViewHolder> {
    private List<LanguageModel> languageModelList;
    private IClickItemLanguage iClickItemLanguage;
    private Context context;

    public LanguageStartAdapter(List<LanguageModel> languageModelList, IClickItemLanguage listener, Context context) {
        this.languageModelList = languageModelList;
        this.iClickItemLanguage = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public LangugeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_language, parent, false);
        return new LangugeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LangugeViewHolder holder, int position) {
        LanguageModel languageModel = languageModelList.get(position);
        if (languageModel == null) {
            return;
        }
        holder.tvLang.setText(languageModel.getName());
        if (languageModel.getActive()) {
            holder.rdbCheck.setChecked(true);
        } else {
            holder.rdbCheck.setChecked(false);
        }
        holder.rdbCheck.setClickable(false);

        if (position == languageModelList.size() - 1) {
            holder.linesItem.setVisibility(View.GONE);
        } else {
            holder.linesItem.setVisibility(View.VISIBLE);
        }

        switch (languageModel.getCode()) {
            case "fr":
                Glide.with(context).asBitmap().load(R.drawable.ic_lang_fr).into(holder.icLang);
                break;
            case "es":
                Glide.with(context).asBitmap().load(R.drawable.ic_lang_es).into(holder.icLang);
                break;
            case "zh":
                Glide.with(context).asBitmap().load(R.drawable.ic_lang_zh).into(holder.icLang);
                break;
            case "in":
                Glide.with(context).asBitmap().load(R.drawable.ic_lang_in).into(holder.icLang);
                break;
            case "hi":
                Glide.with(context).asBitmap().load(R.drawable.ic_lang_hi).into(holder.icLang);
                break;
            case "de":
                Glide.with(context).asBitmap().load(R.drawable.ic_lang_ge).into(holder.icLang);
                break;
            case "pt":
                Glide.with(context).asBitmap().load(R.drawable.ic_lang_pt).into(holder.icLang);
                break;
            case "en":
                Glide.with(context).asBitmap().load(R.drawable.ic_lang_en).into(holder.icLang);
                break;
        }

        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCheck(languageModel.getCode());
                iClickItemLanguage.onClickItemLanguage(languageModel.getCode());
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        if (languageModelList != null) {
            return languageModelList.size();
        } else {
            return 0;
        }
    }

    public class LangugeViewHolder extends RecyclerView.ViewHolder {
        private RadioButton rdbCheck;
        private TextView tvLang;
        private LinearLayout layoutItem;
        private ImageView icLang;
        private View linesItem;

        public LangugeViewHolder(@NonNull View itemView) {
            super(itemView);
            rdbCheck = itemView.findViewById(R.id.rdbCheck);
            icLang = itemView.findViewById(R.id.icLang);
            tvLang = itemView.findViewById(R.id.tvLang);
            layoutItem = itemView.findViewById(R.id.layoutItem);
            linesItem = itemView.findViewById(R.id.linesItem);
        }
    }

    public void setCheck(String code) {
        for (LanguageModel item : languageModelList) {
            if (item.getCode().equals(code)) {
                item.setActive(true);
            } else {
                item.setActive(false);
            }

        }
        notifyDataSetChanged();
    }
}
