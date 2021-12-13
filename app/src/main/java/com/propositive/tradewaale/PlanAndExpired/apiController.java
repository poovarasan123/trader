package com.propositive.tradewaale.PlanAndExpired;

import com.propositive.tradewaale.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.propositive.tradewaale.PlanAndExpired.apiset;

public class apiController {

    public static apiController clientobject;
    public static Retrofit retrofit;

    apiController(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.29.178/test/get_plans.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized apiController getInstance(){
        if (clientobject==null){
            clientobject = new apiController();
        }
        return clientobject;
    }

    apiset getapi(){
        return retrofit.create(apiset.class);
    }

}
