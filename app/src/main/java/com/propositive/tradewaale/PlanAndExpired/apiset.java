package com.propositive.tradewaale.PlanAndExpired;

import com.propositive.tradewaale.notification.NotificationModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface apiset {

    @GET("get_notifications.php")
    Call<List<PlanModel>> getdata();

}
