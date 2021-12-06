package com.propositive.tradewaale.advisory.tabs.equity.extendView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.propositive.tradewaale.R;
import com.propositive.tradewaale.advisory.tabs.derivative.extendView.DerivativeCallHistoryModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EquityCallHistoryAdapter extends RecyclerView.Adapter<EquityCallHistoryAdapter.ViewHolder> {

    Context context;
    List<EquityCallHistoryModel> data;

    String open = "Add";
    String close = "Exit";

    public EquityCallHistoryAdapter(Context context, List<EquityCallHistoryModel> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public EquityCallHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.call_history_adapter, parent, false);

        return new EquityCallHistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EquityCallHistoryAdapter.ViewHolder holder, int position) {

        if (data.get(position).getH_calls_method().equals("open")){
            holder.indicator.setImageResource(R.drawable.round_open);
            holder.title.setText(open);
            holder.stock_state.setTextColor(context.getResources().getColor(R.color.orange));
        }

        if (data.get(position).getH_calls_method().equals("close")) {
            holder.indicator.setImageResource(R.drawable.round_close);
            holder.title.setText(close);
            holder.stock_state.setTextColor(context.getResources().getColor(R.color.red));
        }

        holder.stock_state.setText(data.get(position).getH_calls_method());
        holder.market_name.setText(data.get(position).getH_symbol());
        holder.recommend_pr.setText(data.get(position).getH_reco_pr());
        holder.targetpr.setText(data.get(position).getH_target());

        holder.date.setText(dateChanger(data.get(position).getH_updated()));
        holder.time.setText(data.get(position).getH_updated_time());

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView indicator;

        TextView title, stock_state, market_name, recommend_pr, targetpr, date, time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            indicator = itemView.findViewById(R.id.trend_status_color);
            title = itemView.findViewById(R.id.trend_titile_txt);
            stock_state = itemView.findViewById(R.id.stock_status);
            market_name = itemView.findViewById(R.id.market_name_txt);
            recommend_pr = itemView.findViewById(R.id.current_value);
            targetpr = itemView.findViewById(R.id.reduce_value);
            date = itemView.findViewById(R.id.call_date_txt);
            time = itemView.findViewById(R.id.call_time_txt);
        }
    }

    public String dateChanger(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd-MMM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
}
