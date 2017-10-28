package com.jomblo_terhormat.badjigurrestopelayan.networking.udacity;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.jomblo_terhormat.badjigurrestopelayan.activiy.MainActivity;
import com.jomblo_terhormat.badjigurrestopelayan.entity.Produk;

import java.util.List;



/**
 * Created by GOODWARE1 on 8/30/2017.
 */

public class ProdukLoader extends AsyncTaskLoader<List<Produk>> {



    public ProdukLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        if (MainActivity.mProduk == null)
            forceLoad();
    }

    @Override
    public List<Produk> loadInBackground() {

        return QueryUtils.fetchData(Produk.BASE_PATH + Produk.JSON_REPLY) ;
    }

}