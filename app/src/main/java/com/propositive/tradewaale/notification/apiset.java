package com.propositive.tradewaale.notification;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface apiset {

    @GET("get_notifications.php")
    Call<List<NotificationModel>> getdata();

}
