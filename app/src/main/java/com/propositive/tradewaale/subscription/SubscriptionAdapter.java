package com.propositive.tradewaale.subscription;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.propositive.tradewaale.R;
import com.propositive.tradewaale.notification.NotificationListAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.ViewHolder> {

    private static final String TAG = "";

    Context context;
    List<SubscriptionModel> planHistory;

    int Normal = R.drawable.normal_trader;
    int Basic = R.drawable.basic_trader2;
    int Smart_Trader = R.drawable.smart_trader;

    public SubscriptionAdapter(Context context, List<SubscriptionModel> planHistory) {
        this.context = context;
        this.planHistory = planHistory;
    }

    @NonNull
    @Override
    public SubscriptionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subscription_history_adapter, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionAdapter.ViewHolder holder, int position) {

        if (planHistory.get(position).getPlan_id().equals("1")){
            holder.trade_image.setImageResource(Normal);
        }
        if (planHistory.get(position).getPlan_id().equals("2")){
            holder.trade_image.setImageResource(Basic);
        }
        if (planHistory.get(position).getPlan_id().equals("3")){
            holder.trade_image.setImageResource(Smart_Trader);
        }

        holder.planName.setText(planHistory.get(position).getPlan_name());
        holder.planDuration.setText(planHistory.get(position).getPlan_validity());

        //holder.planStatus.setText(planHistory.get(position).get);

        holder.planSloan.setText(planHistory.get(position).getPlan_slogan());
        holder.planCost.setText(planHistory.get(position).getPlan_price());
        holder.paymentId.setText(planHistory.get(position).getPaymentId());
        holder.startDate.setText(planHistory.get(position).getStart_date());
        holder.endDate.setText(planHistory.get(position).getEnd_date());
        holder.planDiscription.setText(planHistory.get(position).getPlan_details());
        holder.paymentStatus.setText(planHistory.get(position).getPayment_status());

        Log.d(TAG, "onBindViewHolder: plan id " + planHistory.get(position).getPlan_id());
        Log.d(TAG, "onBindViewHolder: plan name " + planHistory.get(position).getPlan_name());
        Log.d(TAG, "onBindViewHolder: plan duration " + planHistory.get(position).getPlan_validity());
        Log.d(TAG, "onBindViewHolder: plan status " + planHistory.get(position).getPlan_status());
        Log.d(TAG, "onBindViewHolder: plan slogan " + planHistory.get(position).getPlan_slogan());
        Log.d(TAG, "onBindViewHolder: plan price " + planHistory.get(position).getPlan_price());
        Log.d(TAG, "onBindViewHolder: plan details " + planHistory.get(position).getPlan_details());
        Log.d(TAG, "onBindViewHolder: payment id " + planHistory.get(position).getPaymentId());
        Log.d(TAG, "onBindViewHolder: payment status " + planHistory.get(position).getPayment_status());
        Log.d(TAG, "onBindViewHolder: start date " + planHistory.get(position).getStart_date());
        Log.d(TAG, "onBindViewHolder: end date " + planHistory.get(position).getEnd_date());

    }

    @Override
    public int getItemCount() {
        return planHistory.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView trade_image;
        TextView planName, planDuration, planStatus, planSloan, planCost, paymentId, startDate, endDate, planDiscription, paymentStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            trade_image = itemView.findViewById(R.id.trade_mark);
            planName = itemView.findViewById(R.id.plan_name_txt);
            planDuration = itemView.findViewById(R.id.plan_duration_txt);
            planStatus = itemView.findViewById(R.id.plan_status_txt);
            planSloan = itemView.findViewById(R.id.plan_slogan_txt);
            planCost = itemView.findViewById(R.id.plan_cost_txt);
            paymentId = itemView.findViewById(R.id.payment_id_txt);
            startDate = itemView.findViewById(R.id.start_date_txt);
            endDate = itemView.findViewById(R.id.end_date_txt);
            planDiscription = itemView.findViewById(R.id.plan_discription_txt);
            paymentStatus = itemView.findViewById(R.id.payment_status_txt);



        }
    }
}
