package com.propositive.traderwaale.advisory.tabs.equity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface apiset {

    @GET("get_data_equity.php")
    Call<List<EquityModel>> getdata();

}
