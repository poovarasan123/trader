package com.propositive.tradewaale.advisory.tabs.derivative;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.propositive.tradewaale.R;
import com.propositive.tradewaale.advisory.tabs.derivative.extendView.DerivativeExtendViewActivity;

import java.util.List;

public class DerivativeAdapter extends RecyclerView.Adapter<DerivativeAdapter.ViewHolder>{

    Context context;
    List<DerivativeModel> data;

    private static final String TAG = "EquityAdapter";

    public DerivativeAdapter(List<DerivativeModel> data) {
        this.data = data;
    }

    private ItemClickListener itemClickListener;

    public interface ItemClickListener{
        void onItemClickListener(int position);
    }

    public void setItemClickListener(DerivativeAdapter.ItemClickListener listener){
        itemClickListener= listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.derivative_adapter, parent, false);
        context = parent.getContext();

        return new ViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {

        int position = pos;

        //TODO: buy and sell color
        if (data.get(position).getBuy_value().equals("buy")){
            holder.bsStatus.setTextColor(context.getResources().getColor(R.color.green));
            holder.bsStatus.setBackgroundResource(R.drawable.buy_stroke);
        }else{
            holder.bsStatus.setTextColor(context.getResources().getColor(R.color.red));
            holder.bsStatus.setBackgroundResource(R.drawable.sell_stroke);
        }

        Log.d("TAG", "onBindViewHolder: " + data.get(position).getBuy_value());
        Log.d("TAG", "onBindViewHolder: " + data.get(position).getCalls_method());
        Log.d("TAG", "onBindViewHolder: " + data.get(position).getTarget2());


        //TODO: open and close status
        if (data.get(position).getCalls_method().equals("open")){
            holder.opStatus.setTextColor(context.getResources().getColor(R.color.orange));
            holder.tagStatus.setBackgroundColor(context.getResources().getColor(R.color.orange));
        }else{
            holder.opStatus.setTextColor(context.getResources().getColor(R.color.red));
            holder.tagStatus.setBackgroundColor(context.getResources().getColor(R.color.red));
        }

        holder.name_ltd.setText(data.get(position).getSymbol());
        holder.bsStatus.setText(data.get(position).getBuy_value());
        holder.opStatus.setText(data.get(position).getCalls_method());
        holder.reco_price.setText(String.valueOf(data.get(position).getReco_pr()));
        holder.tergetStart.setText(String.valueOf(data.get(position).getTarget()));
        holder.targetEnd.setText(String.valueOf(data.get(position).getTarget2()));
        holder.stockloss.setText(String.valueOf(data.get(position).getStop_loss()));

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "position :" + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick: time ->" + data.get(holder.getAdapterPosition()).getSel_time());
                Log.d(TAG, "onClick: date ->" + data.get(holder.getAdapterPosition()).getSel_date());


                Intent i = new Intent(v.getContext(), DerivativeExtendViewActivity.class);

                Log.d(TAG, "onClick: name testing " + data.get(position).getSymbol());

                i.putExtra("name", data.get(position).getSymbol());
                i.putExtra("rateStatus", data.get(position).getBuy_value());
                i.putExtra("stockStatus", data.get(position).getCalls_method());
                i.putExtra("recoValue", data.get(position).getReco_pr());
                i.putExtra("tcValueStart", data.get(position).getTarget());
                i.putExtra("tcValueEnd", data.get(position).getTarget2());
                i.putExtra("term", data.get(position).getExp_term());
                i.putExtra("date", data.get(position).getSel_date());
                i.putExtra("time", data.get(position).getSel_time());

                v.getContext().startActivity(i);



            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void updateList(List<DerivativeModel> flist) {
        data = flist;
        notifyDataSetChanged();
    }

    public void TermList(List<DerivativeModel> termList) {
        data = termList;
        notifyDataSetChanged();
    }

    public void stockList(List<DerivativeModel> stockList) {
        data = stockList;
        notifyDataSetChanged();

    }

    public void rateList(List<DerivativeModel> rateList) {
        data = rateList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final View tagStatus;
        final View rootView;
        final TextView name_ltd;
        final TextView bsStatus;
        final TextView opStatus;
        final TextView reco_price;
        final TextView tergetStart;
        final TextView targetEnd;
        final TextView stockloss;
        final CardView card;

        public ViewHolder(@NonNull View itemView, final  ItemClickListener listener) {
            super(itemView);

            rootView = itemView;

            tagStatus = itemView.findViewById(R.id.tag_status_view);

            name_ltd = itemView.findViewById(R.id.name_ltd_txt);
            bsStatus = itemView.findViewById(R.id.buy_or_sell);
            opStatus = itemView.findViewById(R.id.open_or_close);
            reco_price = itemView.findViewById(R.id.reco_value);
            tergetStart = itemView.findViewById(R.id.target_value_start);
            targetEnd = itemView.findViewById(R.id.target_value_end);
            stockloss = itemView.findViewById(R.id.sl_value);
            card = itemView.findViewById(R.id.card_adapter);


            name_ltd.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            name_ltd.setSelected(true);


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