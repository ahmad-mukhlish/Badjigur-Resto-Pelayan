package com.jomblo_terhormat.badjigurrestopelayan.networking.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.jomblo_terhormat.badjigurrestopelayan.networking.endpoint.EndPoint.BASE_URL;

/**
 * Created by hiros on 10/23/2017.
 */

public class ApiClient {
    public static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
