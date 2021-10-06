package com.propositive.tradewaale.notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.propositive.tradewaale.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.ViewHolder> {

    Context context;
    List<NotificationModel> notifyList;

    public NotificationListAdapter(List<NotificationModel> notifyList) {
        this.notifyList = notifyList;
    }



    @NonNull
    @Override
    public NotificationListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_list_adapter, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationListAdapter.ViewHolder holder, int position) {

        holder.title.setText(notifyList.get(position).getNotification_title());
        holder.body_message.setText(notifyList.get(position).getBody_message());
        holder.date.setText(notifyList.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return notifyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, body_message, date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title_txt);

            body_message = itemView.findViewById(R.id.body_txt);
            date = itemView.findViewById(R.id.date_text);
        }
    }
}
