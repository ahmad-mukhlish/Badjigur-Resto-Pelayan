package com.jomblo_terhormat.badjigurrestopelayan.networking.udacity;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.jomblo_terhormat.badjigurrestopelayan.activiy.MainActivity;
import com.jomblo_terhormat.badjigurrestopelayan.entity.Produk;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GOODWARE1 on 8/30/2017.
 */

public class ProdukLoader extends AsyncTaskLoader<List<List<Produk>>> {

    private static final String LOG_TAG = ProdukLoader.class.getName();
    private String mURl[] = new String[3];


    public ProdukLoader(Context context, String[] URl) {
        super(context);
        this.mURl = URl;
    }


    public ProdukLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        if (MainActivity.mProduk == null)
        forceLoad();
        Log.v(LOG_TAG, "On Start Loading");
    }

    @Override
    public List<List<Produk>> loadInBackground() {
        if (mURl == null) {
            return null;
        }

        Log.v(LOG_TAG, "Loading Background");

        List<List<Produk>> lists = new ArrayList<>();

        lists.add(QueryUtils.fetchData(Produk.DUMMY_JSON_MAKANAN));
        lists.add(QueryUtils.fetchData(Produk.DUMMY_JASON_MINUMAN));
        lists.add(QueryUtils.fetchData(Produk.DUMMY_JASON_DESSERT));



        return lists;
    }
}