package com.propositive.tradewaale.advisory.tabs.equity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface apiset {

    @GET("get_equity.php")
    Call<List<EquityModel>> getdata();

}
