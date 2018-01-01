package com.jomblo_terhormat.badjigurrestopelayan.networking;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.jomblo_terhormat.badjigurrestopelayan.activity.MainActivity;
import com.jomblo_terhormat.badjigurrestopelayan.entity.Produk;

import java.util.List;

public class ProdukLoader extends AsyncTaskLoader<List<Produk>> {

    private final String LOG_TAG = ProdukLoader.class.getName();

    private String mUrl;

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
        return QueryUtils.fetchMakanan(mUrl) ;
    }

}