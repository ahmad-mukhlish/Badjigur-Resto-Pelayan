package com.jomblo_terhormat.badjigurrestopelayan.networking.retrofit;



import com.jomblo_terhormat.badjigurrestopelayan.entity.Produk;

import java.util.List;
import retrofit2.Call;

import retrofit2.http.GET;

/**
 * Created by hiros on 10/23/2017.
 */

public interface ApiInterface {
    @GET(Produk.JSON_REPLY_MENU)
    Call<List<Produk>> getMakanan();
}
