package com.master.navdrawerbottomnva.home;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.master.navdrawerbottomnva.R;

import java.util.ArrayList;

public class newsAdapter extends RecyclerView.Adapter<newsAdapter.ViewHolder> {

    Context context;
    ArrayList<newsModel> newses;

    private newsAdapter.ItemClickListener itemClickListener;


    public interface ItemClickListener{
        void onItemClickListener(int position);
    }

    public void setItemClickListener(newsAdapter.ItemClickListener listener){
        itemClickListener= listener;
    }

    public newsAdapter(Context context, ArrayList<newsModel> newses) {
        this.context = context;
        this.newses = newses;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_adapter, parent, false);
        context = parent.getContext();
        return new ViewHolder(v,itemClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.imageView.setImageResource(newses.get(position).getImageNews());
        holder.title.setText(newses.get(position).getTitle_text());
        holder.date.setText(newses.get(position).getDate_text());
        holder.comment.setText(newses.get(position).comment_text);

//        holder.share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Snackbar.make(v,"share under construction!", Snackbar.LENGTH_SHORT).show();
//            }
//        });
//
//        holder.bookmark.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Snackbar.make(v,"share bookmark construction!", Snackbar.LENGTH_SHORT).show();
//            }
//        });

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), NewsExtendActivity.class);
                context.startActivity(i);
                Toast.makeText(context.getApplicationContext(), "position :" + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return newses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;    //, share, bookmark;
        TextView title, date, comment;
        LinearLayout layout;

        public ViewHolder(@NonNull View itemView, final ItemClickListener listener) {
            super(itemView);

            imageView = itemView.findViewById(R.id.news_image);
            title = itemView.findViewById(R.id.text_title);
            date = itemView.findViewById(R.id.text_date);
            comment = itemView.findViewById(R.id.text_comment);

//            share = itemView.findViewById(R.id.share_link);
//            bookmark = itemView.findViewById(R.id.bookmark_news);

            layout = itemView.findViewById(R.id.linear);

            itemView.setOnClickListener(v -> {
                if(listener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onItemClickListener(position);
                    }
                }
            });
        }
    }
}
