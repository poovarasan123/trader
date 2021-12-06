package com.propositive.tradewaale.PlanAndExpired;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.propositive.tradewaale.R;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {

    Context context;
    final List<PlanModel> planList;

    private static final String TAG = "plan Adapter";

    public PlanAdapter(List<PlanModel> planList) {
        this.planList = planList;
    }


    @NonNull
    @Override
    public PlanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_layout, parent, false);
         return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanAdapter.ViewHolder holder, int position) {

        holder.planName.setText(planList.get(position).getPlan_name());
        holder.planDetail.setText(planList.get(position).getPlan_details());

    }

    @Override
    public int getItemCount() {
        return planList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView planName, planDetail;
        Button activatePlan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            planName = itemView.findViewById(R.id.plan_name);
            planDetail = itemView.findViewById(R.id.plan_details);
            activatePlan = itemView.findViewById(R.id.get_plan);
        }
    }
}
