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

        return new ViewHolder(view,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //TODO: buy and sell color
        if (data.get(position).getRate_status().equals("buy")){
            holder.bsStatus.setTextColor(context.getResources().getColor(R.color.green));
            holder.bsStatus.setBackgroundResource(R.drawable.buy_stroke);
        }else{
            holder.bsStatus.setTextColor(context.getResources().getColor(R.color.red));
            holder.bsStatus.setBackgroundResource(R.drawable.sell_stroke);
        }

        Log.d("TAG", "onBindViewHolder: " + data.get(position).getRate_status());
        Log.d("TAG", "onBindViewHolder: " + data.get(position).getStock_status());
        Log.d("TAG", "onBindViewHolder: end " + data.get(position).getTarget_value_end());


        //TODO: open and close status
        if (data.get(position).getStock_status().equals("open")){
            holder.opStatus.setTextColor(context.getResources().getColor(R.color.orange));
            holder.tagStatus.setBackgroundColor(context.getResources().getColor(R.color.orange));
        }else{
            holder.opStatus.setTextColor(context.getResources().getColor(R.color.red));
            holder.tagStatus.setBackgroundColor(context.getResources().getColor(R.color.red));
        }

        holder.name_ltd.setText(data.get(position).getName());
        holder.bsStatus.setText(data.get(position).getRate_status());
        holder.opStatus.setText(data.get(position).getStock_status());
        holder.reco_price.setText(String.valueOf(data.get(position).getReco_price()));
        holder.tergetStart.setText(String.valueOf(data.get(position).getTarget_value_start()));
        holder.targetEnd.setText(String.valueOf(data.get(position).getTarget_value_end()));
        holder.stockloss.setText(String.valueOf(data.get(position).getStock_loss()));

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "position :" + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick: time ->" + data.get(holder.getAdapterPosition()).getPost_time());
                Log.d(TAG, "onClick: date ->" + data.get(holder.getAdapterPosition()).getPost_date());


                Intent i = new Intent(v.getContext(), DerivativeExtendViewActivity.class);

                i.putExtra("name", data.get(position).getName());
                i.putExtra("rateStatus", data.get(position).getRate_status());
                i.putExtra("stockStatus", data.get(position).getStock_status());
                i.putExtra("recoValue", data.get(position).getReco_price());
                i.putExtra("tcValueStart", data.get(position).getTarget_value_start());
                i.putExtra("tcValueEnd", data.get(position).getTarget_value_end());
                i.putExtra("term", data.get(position).getTerms());
                i.putExtra("date", data.get(position).getPost_date());
                i.putExtra("time", data.get(position).getPost_time());

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

    public class ViewHolder extends RecyclerView.ViewHolder {

        View tagStatus, rootView;
        TextView name_ltd, bsStatus, opStatus, reco_price, tergetStart, targetEnd, stockloss;
        CardView card;

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