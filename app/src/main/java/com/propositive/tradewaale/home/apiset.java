package com.propositive.tradewaale.home;

import com.propositive.tradewaale.advisory.tabs.derivative.DerivativeModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface apiset {

    @GET("get_news.php")
    Call<List<newsModel>> getdata();

}
