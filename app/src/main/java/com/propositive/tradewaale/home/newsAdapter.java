package com.propositive.tradewaale.home;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.propositive.tradewaale.R;

import java.util.List;

public class newsAdapter extends RecyclerView.Adapter<newsAdapter.ViewHolder> {

    Context context;
    final List<newsModel> newsList;

    private newsAdapter.ItemClickListener itemClickListener;

    private static final String TAG = "News Adapter";

    public newsAdapter(List<newsModel> newsList) {
        this.newsList = newsList;
    }


    public interface ItemClickListener{
        void onItemClickListener(int position);
    }

    public void setItemClickListener(newsAdapter.ItemClickListener listener){
        itemClickListener= listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_adapter, parent, false);
        context = parent.getContext();
        return new ViewHolder(v, itemClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //Picasso.get().load("http://192.168.33.211/trader/images/" +newsList.get(position).getNews_image()).into(holder.imageView);
        String desc = newsList.get(position).getDescription();
        holder.title.setText(newsList.get(position).getNews_title());
        holder.date.setText(newsList.get(position).getPost_date());
        Glide.with(holder.imageView.getContext()).load("http://192.168.33.211/trader/images/" + newsList.get(position).getNews_image()).into(holder.imageView);

        Log.e(TAG, "onBindViewHolder: image " + newsList.get(position).getNews_image() );
        Log.e(TAG, "onBindViewHolder: title " + newsList.get(position).getNews_title() );
        Log.e(TAG, "onBindViewHolder: desc " + newsList.get(position).getDescription() );
        Log.e(TAG, "onBindViewHolder: date" + newsList.get(position).getPost_date() );

        String image = newsList.get(position).getNews_image();
        String title = newsList.get(position).getNews_title();
        String message = newsList.get(position).getDescription();
        String date = newsList.get(position).getPost_date();

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
                i.putExtra("image", image);
                i.putExtra("title", title);
                i.putExtra("message", message);
                i.putExtra("date", date);
                context.startActivity(i);
                Toast.makeText(context.getApplicationContext(), "position :" + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;    //, share, bookmark;
        final TextView title;
        final TextView date;
        final CardView layout;

        public ViewHolder(@NonNull View itemView, final ItemClickListener listener) {
            super(itemView);

            imageView = itemView.findViewById(R.id.news_image);
            title = itemView.findViewById(R.id.text_title);
            date = itemView.findViewById(R.id.text_date);

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
