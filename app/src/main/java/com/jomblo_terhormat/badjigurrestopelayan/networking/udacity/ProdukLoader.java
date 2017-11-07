package com.jomblo_terhormat.badjigurrestopelayan.networking.udacity;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.jomblo_terhormat.badjigurrestopelayan.activity.MainActivity;
import com.jomblo_terhormat.badjigurrestopelayan.entity.Produk;

import java.util.List;

import retrofit2.Call;


/**
 * Created by GOODWARE1 on 8/30/2017.
 */

public class ProdukLoader extends AsyncTaskLoader<List<Produk>> {


    private String mUrl;
    Call<List<Produk>> mCall;

    public ProdukLoader(Context context, String url) {
        super(context);
        this.mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        if (MainActivity.mProduk == null)
            forceLoad();
    }

    @Override
    public List<Produk> loadInBackground() {
        return QueryUtils.fetchData(mUrl) ;
    }

}