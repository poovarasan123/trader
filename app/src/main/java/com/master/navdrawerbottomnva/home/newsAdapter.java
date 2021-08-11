package com.master.navdrawerbottomnva.home;

import android.content.Context;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.master.navdrawerbottomnva.R;

import java.util.ArrayList;

public class newsAdapter extends RecyclerView.Adapter<newsAdapter.ViewHolder> {

    Context context;
    ArrayList<newsModel> newses;

    public newsAdapter(Context context, ArrayList<newsModel> newses) {
        this.context = context;
        this.newses = newses;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_adapter, parent, false);
//        LayoutInflater inflater = LayoutInflater.from(context.getApplicationContext());
//        View view = inflater.inflate(R.layout.news_adapter, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull newsAdapter.ViewHolder holder, int position) {

        holder.imageView.setImageResource(newses.get(position).getImageNews());
        holder.title.setText(newses.get(position).getTitle_text());
        holder.date.setText(newses.get(position).getDate_text());
        holder.comment.setText(newses.get(position).comment_text);

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"share under construction!", Snackbar.LENGTH_SHORT).show();
            }
        });

        holder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"share bookmark construction!", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return newses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, share, bookmark;
        TextView title, date, comment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.news_image);
            title = itemView.findViewById(R.id.text_title);
            date = itemView.findViewById(R.id.text_date);
            comment = itemView.findViewById(R.id.text_comment);

            share = itemView.findViewById(R.id.share_link);
            bookmark = itemView.findViewById(R.id.bookmark_news);
        }
    }
}
