package com.propositive.tradewaale.home;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface apiset {

    @GET("get_news.php")
    Call<List<newsModel>> getdata();

}
