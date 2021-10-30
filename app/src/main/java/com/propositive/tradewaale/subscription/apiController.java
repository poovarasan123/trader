package com.propositive.tradewaale.subscription;

import com.propositive.tradewaale.Constants;
import com.propositive.tradewaale.subscription.apiset;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class apiController {
    private static apiController clientobject;
    private static Retrofit retrofit;

    apiController(){
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.PLAN_HISTORY)
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
