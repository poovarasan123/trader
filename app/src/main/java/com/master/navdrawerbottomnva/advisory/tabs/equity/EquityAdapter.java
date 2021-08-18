package com.master.navdrawerbottomnva.advisory.tabs.equity;

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

import com.master.navdrawerbottomnva.R;
import com.master.navdrawerbottomnva.advisory.tabs.equity.extendView.EquityExtendViewActivity;

import java.util.List;

public class EquityAdapter extends RecyclerView.Adapter<EquityAdapter.ViewHolder>  implements View.OnClickListener {

    Context context;
    List<EquityModel> data;
    RecyclerView recyclerView;

    private static final String TAG = "EquityAdapter";

    public EquityAdapter(List<EquityModel> data) {
        this.data = data;
    }

    public EquityAdapter(RecyclerView rcv){
        this.recyclerView = rcv;
    }

    private ItemClickListener itemClickListener;

    @Override
    public void onClick(View v) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: " + v);
            }
        });
    }


    public interface ItemClickListener{
        void onItemClickListener(int position);
    }

    public void setItemClickListener(EquityAdapter.ItemClickListener listener){
        itemClickListener= listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.equity_adapter, parent, false);
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


        //TODO: open and close status
        if (data.get(position).getStock_status().equals("open")){
            holder.opStatus.setTextColor(context.getResources().getColor(R.color.orange));
            holder.tcTxt.setText("TARGET PRICE");
            holder.apTxt.setText("POTENTIAL RETURN");
            holder.tagStatus.setBackgroundColor(context.getResources().getColor(R.color.orange));
        }else{
            holder.opStatus.setTextColor(context.getResources().getColor(R.color.red));
            holder.tcTxt.setText("CLOSED PRICE");
            holder.apTxt.setText("ACTUAL RETURN");
            holder.tagStatus.setBackgroundColor(context.getResources().getColor(R.color.red));
        }

        holder.name_ltd.setText(data.get(position).getName());
        holder.bsStatus.setText(data.get(position).getRate_status());
        holder.opStatus.setText(data.get(position).getStock_status());
        holder.reco_price.setText(String.valueOf(data.get(position).getReco_value()));
        holder.tcValue.setText(String.valueOf(data.get(position).getTc_price()));
        holder.term.setText(String.valueOf(data.get(position).getFilter()));

        //TODO: percentage calculation
        float min = data.get(position).getTc_price() - data.get(position).getReco_value();

        float total = min / data.get(position).getReco_value();

        float percent = total * 100;

        holder.apValue.setText(percent + "%");

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "position :" + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick: time ->" + data.get(holder.getAdapterPosition()).getPost_time());
                Log.d(TAG, "onClick: date ->" + data.get(holder.getAdapterPosition()).getPost_date());

                Intent i = new Intent(v.getContext(), EquityExtendViewActivity.class);

                i.putExtra("name", data.get(position).getName());
                i.putExtra("rateStatus", data.get(position).getRate_status());
                i.putExtra("stockStatus", data.get(position).getStock_status());
                i.putExtra("recoValue", data.get(position).getReco_value());
                i.putExtra("tcValue", data.get(position).getTc_price());
                i.putExtra("term", data.get(position).getFilter());
                i.putExtra("apValue", percent);
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

    public void updateList(List<EquityModel> flist) {
        data = flist;
        notifyDataSetChanged();
    }

    public void TermList(List<EquityModel> termList) {
        data = termList;
        notifyDataSetChanged();
    }

    public void stockList(List<EquityModel> stockList) {
        data = stockList;
        notifyDataSetChanged();

    }

    public void rateList(List<EquityModel> rateList) {
        data = rateList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View tagStatus, rootView;
        TextView name_ltd, bsStatus, opStatus, reco_price, tcTxt, tcValue, apTxt, apValue, term;
        CardView card;

        public ViewHolder(@NonNull View itemView, final  ItemClickListener listener) {
            super(itemView);

            rootView = itemView;

            tagStatus = itemView.findViewById(R.id.tag_status_view);

            name_ltd = itemView.findViewById(R.id.name_ltd_txt);
            bsStatus = itemView.findViewById(R.id.buy_or_sell);
            opStatus = itemView.findViewById(R.id.open_or_close);
            reco_price = itemView.findViewById(R.id.reco_value);
            tcTxt = itemView.findViewById(R.id.target_or_close_txt);
            tcValue = itemView.findViewById(R.id.target_or_close_value);
            apTxt = itemView.findViewById(R.id.pot_or_act_txt);
            apValue = itemView.findViewById(R.id.pot_or_act_value);
            term = itemView.findViewById(R.id.term_txt);
            card = itemView.findViewById(R.id.card_adapter);


            name_ltd.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            name_ltd.setSelected(true);

            apTxt.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            apTxt.setSelected(true);

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