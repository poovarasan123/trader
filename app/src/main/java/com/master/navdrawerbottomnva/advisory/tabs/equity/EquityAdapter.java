package com.master.navdrawerbottomnva.advisory.tabs.equity;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.master.navdrawerbottomnva.R;

import java.util.ArrayList;
import java.util.List;

public class EquityAdapter extends RecyclerView.Adapter<EquityAdapter.ViewHolder> {

    Context context;
    List<EquityModel> data;

    public EquityAdapter(List<EquityModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.equity_adapter, parent, false);
        context = parent.getContext();

        return new ViewHolder(view);
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
        //holder.apValue.setText(String.valueOf(data.get(position).getAp_return()));

        //TODO: percentage calculation
        float min = data.get(position).getTc_price() - data.get(position).getReco_value();

        float total = min / data.get(position).getReco_value();

        float percent = total * 100;

        holder.apValue.setText(percent + "%");

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

    public class ViewHolder extends RecyclerView.ViewHolder {

        View tagStatus;
        TextView name_ltd, bsStatus, opStatus, reco_price, tcTxt, tcValue, apTxt, apValue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tagStatus = itemView.findViewById(R.id.tag_status_view);

            name_ltd = itemView.findViewById(R.id.name_ltd_txt);
            bsStatus = itemView.findViewById(R.id.buy_or_sell);
            opStatus = itemView.findViewById(R.id.open_or_close);
            reco_price = itemView.findViewById(R.id.reco_value);
            tcTxt = itemView.findViewById(R.id.target_or_close_txt);
            tcValue = itemView.findViewById(R.id.target_or_close_value);
            apTxt = itemView.findViewById(R.id.pot_or_act_txt);
            apValue = itemView.findViewById(R.id.pot_or_act_value);


            name_ltd.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            name_ltd.setSelected(true);

            apTxt.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            apTxt.setSelected(true);

        }
    }
}