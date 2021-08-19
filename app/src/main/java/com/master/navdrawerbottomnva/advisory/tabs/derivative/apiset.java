package com.master.navdrawerbottomnva.advisory.tabs.derivative;

import com.master.navdrawerbottomnva.advisory.tabs.equity.EquityModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface apiset {

    @GET("get_data_derivative.php")
    Call<List<DerivativeModel>> getdata();

}
