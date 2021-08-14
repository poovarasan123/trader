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

public class EquityAdapter extends RecyclerView.Adapter<EquityAdapter.ViewHolder> {

    Context context;
    ArrayList<EquityModel> equityList;

    public EquityAdapter(Context context, ArrayList<EquityModel> equityList) {
        this.context = context;
        this.equityList = equityList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.equity_adapter, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //TODO: buy and sell color
        if (equityList.get(position).getRate_status().equals("buy")){
            holder.bsStatus.setTextColor(context.getResources().getColor(R.color.green));
            holder.bsStatus.setBackgroundResource(R.drawable.buy_stroke);
        }else{
            holder.bsStatus.setTextColor(context.getResources().getColor(R.color.red));
            holder.bsStatus.setBackgroundResource(R.drawable.sell_stroke);
        }

        Log.d("TAG", "onBindViewHolder: " + equityList.get(position).getRate_status());
        Log.d("TAG", "onBindViewHolder: " + equityList.get(position).getStock_status());


        //TODO: open and close status
        if (equityList.get(position).getStock_status().equals("open")){
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

        holder.name_ltd.setText(equityList.get(position).getName());
        holder.bsStatus.setText(equityList.get(position).getRate_status());
        holder.opStatus.setText(equityList.get(position).getStock_status());
        holder.reco_price.setText(String.valueOf(equityList.get(position).getReco_value()));
        holder.tcValue.setText(String.valueOf(equityList.get(position).getTc_price()));
        holder.apValue.setText(String.valueOf(equityList.get(position).getAp_return()));



    }

    @Override
    public int getItemCount() {
        return equityList.size();
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
