package com.jomblo_terhormat.badjigurrestopelayan.activity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jomblo_terhormat.badjigurrestopelayan.R;
import com.jomblo_terhormat.badjigurrestopelayan.adapter.MenuTabAdapter;
import com.jomblo_terhormat.badjigurrestopelayan.entity.Produk;
import com.jomblo_terhormat.badjigurrestopelayan.networking.ProdukLoader;
import com.jomblo_terhormat.badjigurrestopelayan.networking.QueryUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Produk>> {

    private final String LOG_TAG = MainActivity.class.getName();

    public static List<Produk> mProduk;
    private ActionBar mActionBar;
    private LinearLayout mLoading;
    private static final int LOADER_ID = 54;
    private Button mBilling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProduk = null;

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

        mBilling = (Button) findViewById(R.id.billing);


    }

    private String[] setTitle() {
        String titles[] = new String[MenuTabAdapter.TOTAL_FRAGMENT];
        titles[0] = getString(R.string.food);
        titles[1] = getString(R.string.beverage);
        titles[2] = getString(R.string.desert);

        return titles;
    }


    @Override
    public Loader<List<Produk>> onCreateLoader(int i, Bundle bundle) {
        if (mProduk == null) {
            return new ProdukLoader(this, Produk.BASE_PATH + Produk.JSON_REPLY_MENU);
        } else
            return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Produk>> loader, List<Produk> produks) {
        if (mProduk == null || mProduk.isEmpty()) {
            mProduk = produks;
        }

        updateUI(mProduk);

    }

    @Override
    public void onLoaderReset(Loader<List<Produk>> loader) {

    }

    private void updateUI(List<Produk> list) {

        mActionBar.show();
        mLoading.setVisibility(View.GONE);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        MenuTabAdapter adapter = new MenuTabAdapter(getSupportFragmentManager(), setTitle(), list);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        mBilling.setOnClickListener(new BillingClicked(this, list));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // TODO move this to navbar

        switch (item.getItemId()) {

            case R.id.waiter: {
                // TODO add notif to kasir here
                Toast.makeText(this, R.string.waiter_called, Toast.LENGTH_SHORT).show();
                break;
            }


            case R.id.logout: {
                mProduk = null;
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                new LogoutAsyncTask(MainActivity.this).execute(Produk.BASE_PATH + Produk.JSON_LOGOUT + Produk.NO_MEJA);
                finish();

            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProduk = null;
    }

    private List<Produk> cartedList(List<Produk> list) {
        List<Produk> carted = new ArrayList<>();
        for (Produk produk : list) {
            if (produk.ismCart())
                carted.add(produk);
        }

        return carted;
    }

    private class BillingClicked implements View.OnClickListener {

        private Context mContext;
        private List<Produk> mProduks;

        BillingClicked(Context mContext, List<Produk> produks) {
            this.mContext = mContext;
            this.mProduks = produks;
        }

        @Override
        public void onClick(View view) {
            if (cartedList(mProduks).size() > 0) {
                Intent intent = new Intent(MainActivity.this, BillingActivity.class);
                intent.putExtra("produks", (ArrayList<Produk>) cartedList(mProduks));
                startActivity(intent);
            } else {
                Toast.makeText(mContext, R.string.no_food_order, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, R.string.main_menu, Toast.LENGTH_SHORT).show();
    }


    private class LogoutAsyncTask extends AsyncTask<String, Void, String> {

        private Context mContext;

        LogoutAsyncTask(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected String doInBackground(String... urls) {

            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            QueryUtils.fetchResponse(urls[0]);

            return null;
        }


        @Override
        protected void onPostExecute(String response) {

            Toast.makeText(mContext, R.string.logout, Toast.LENGTH_SHORT).show();

        }


    }
}

