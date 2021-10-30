package com.propositive.tradewaale.subscription;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.propositive.tradewaale.Constants;
import com.propositive.tradewaale.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubscriptionActivity extends AppCompatActivity {

    private static final String TAG = "Subscription activity";
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;

    SubscriptionAdapter adapter;
    List<SubscriptionModel> dataList = new ArrayList<>();

    String id, pid, pname, pstatus, pslogan, pprice, pvalidity, pdetails, plan_status, sdate, edate;

    SubscriptionModel model;

    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent i = getIntent();
        uid = i.getStringExtra("id");

        loadPlanHistory();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadPlanHistory();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void loadPlanHistory() {
        StringRequest request = new StringRequest(Request.Method.POST, Constants.PLAN_HISTORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e(TAG, "onResponse: response plan:" + response );

                if (response.equals("\n" +
                        "                        No record found!...")) {
                    Toast.makeText(getApplicationContext(), "response plan:" + response, Toast.LENGTH_SHORT).show();
                }


                try {
                    JSONArray history = new JSONArray(response);

                    for (int i=0; i<history.length(); i++){
                        JSONObject jsonObject = history.getJSONObject(i);

                        dataList.add(new SubscriptionModel(
                                jsonObject.getString("plan_id"),
                                jsonObject.getString("plan_name"),
                                jsonObject.getString("plan_validity"),
                                jsonObject.getString("plan_status"),
                                jsonObject.getString("plan_slogan"),
                                jsonObject.getString("plan_price"),
                                jsonObject.getString("paymentId"),
                                jsonObject.getString("payment_status"),
                                jsonObject.getString("start_date"),
                                jsonObject.getString("end_date"),
                                jsonObject.getString("plan_details")
                        ));



                        id = jsonObject.getString("plan_id");
                        pid = jsonObject.getString("paymentId");
                        pname = jsonObject.getString("plan_name");
                        pstatus = jsonObject.getString("plan_status");
                        pslogan = jsonObject.getString("plan_slogan");
                        pprice = jsonObject.getString("plan_price");
                        pvalidity = jsonObject.getString("plan_validity");
                        pdetails = jsonObject.getString("plan_details");
                        plan_status = jsonObject.getString("payment_status");
                        sdate = jsonObject.getString("start_date");
                        edate = jsonObject.getString("end_date");

                        Log.e(TAG, "onResponse: id>" + id );
                        Log.e(TAG, "onResponse: pid>" + pid );
                        Log.e(TAG, "onResponse: pname>" + pname );
                        Log.e(TAG, "onResponse: pstatus>" + pstatus );
                        Log.e(TAG, "onResponse: pslogan>" + pslogan );
                        Log.e(TAG, "onResponse: pprice>" + pprice );
                        Log.e(TAG, "onResponse: pvalidity>" + pvalidity );
                        Log.e(TAG, "onResponse: pdetails>" + pdetails );
                        Log.e(TAG, "onResponse: plan_status>" + plan_status );
                        Log.e(TAG, "onResponse: sdate>" + sdate );
                        Log.e(TAG, "onResponse: edate>" + edate );

//                        SubscriptionModel model = new SubscriptionModel(id, pid, pname, pstatus, pslogan, pprice, pvalidity, pdetails, plan_status, sdate, edate);
//                        dataList.add(model);

                    }

                    adapter = new SubscriptionAdapter(SubscriptionActivity.this, dataList);
                    recyclerView.setAdapter(adapter);
                    //adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: error " + error);
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", uid);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(SubscriptionActivity.this);
        requestQueue.add(request);
    }
}