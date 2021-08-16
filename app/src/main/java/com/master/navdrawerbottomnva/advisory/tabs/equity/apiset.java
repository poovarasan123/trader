package com.master.navdrawerbottomnva.advisory.tabs.equity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface apiset {

    @GET("get_data_equity")
    Call<List<EquityModel>> getdata();

}
