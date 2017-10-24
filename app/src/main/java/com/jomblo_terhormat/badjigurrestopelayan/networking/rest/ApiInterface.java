package com.jomblo_terhormat.badjigurrestopelayan.networking.rest;



import com.jomblo_terhormat.badjigurrestopelayan.entity.Produk;

import java.util.List;
import retrofit2.Call;

import retrofit2.http.GET;

/**
 * Created by hiros on 10/23/2017.
 */

public interface ApiInterface {
    @GET("server.php?operasi=view")
    Call<List<Produk>> getMakanan();
}
