package com.jomblo_terhormat.badjigurrestopelayan.activiy;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.jomblo_terhormat.badjigurrestopelayan.R;
import com.jomblo_terhormat.badjigurrestopelayan.adapter.MenuTabAdapter;
import com.jomblo_terhormat.badjigurrestopelayan.entity.Produk;
import com.jomblo_terhormat.badjigurrestopelayan.networking.ProdukLoader;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<List<Produk>>> {

    public static List<List<Produk>> mProduk = null;
    private ActionBar mActionBar;
    private LinearLayout mLoading;
    private static final int LOADER_ID = 54;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager mConnectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = mConnectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        LinearLayout error = (LinearLayout) findViewById(R.id.error);
        error.setVisibility(View.GONE);

        mLoading = (LinearLayout) findViewById(R.id.loading);

        mActionBar = getSupportActionBar();
        mActionBar.hide();

        if (isConnected) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADER_ID, null, this);
        } else {
            error.setVisibility(View.VISIBLE);
        }


    }

    private String[] setTitle() {
        String titles[] = new String[MenuTabAdapter.TOTAL_FRAGMENT] ;
        titles[0] = "Foods" ;
        titles[1] = "Beverages" ;
        titles[2] = "Deserts" ;

        return titles ;
    }

    @Override
    public Loader<List<List<Produk>>> onCreateLoader(int i, Bundle bundle) {
        if (mProduk == null) {
            return new ProdukLoader(this);
        } else
            return null;
    }

    @Override
    public void onLoadFinished(Loader<List<List<Produk>>> loader, List<List<Produk>> produks) {
        if (mProduk == null) {
            mProduk = produks;
        }

        updateUI(mProduk);

    }

    @Override
    public void onLoaderReset(Loader<List<List<Produk>>> loader) {

    }

    private void updateUI(List<List<Produk>> list) {
        mActionBar.show();
        mLoading.setVisibility(View.GONE);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        MenuTabAdapter adapter = new MenuTabAdapter(getSupportFragmentManager(), setTitle(), list);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProduk = null;
    }
}
