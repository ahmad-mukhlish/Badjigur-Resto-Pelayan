package com.jomblo_terhormat.badjigurrestopelayan.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.jomblo_terhormat.badjigurrestopelayan.networking.retrofit.ApiClient;
import com.jomblo_terhormat.badjigurrestopelayan.networking.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static List<Produk> mProduk;
    private ActionBar mActionBar;
    private LinearLayout mLoading;
    Button billing;

    Call<List<Produk>> mCall;

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
            connectWithRetrofit(this);
        } else {
            error.setVisibility(View.VISIBLE);
        }

        billing = (Button) findViewById(R.id.billing);

    }

    private String[] setTitle() {
        String titles[] = new String[MenuTabAdapter.TOTAL_FRAGMENT];
        titles[0] = "Foods";
        titles[1] = "Beverages";
        titles[2] = "Deserts";

        return titles;
    }


    public void connectWithRetrofit(final Context context) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        if (mCall == null) {
            mCall = apiService.getMakanan();
        }

        mCall.enqueue(new Callback<List<Produk>>() {

            @Override
            public void onResponse(Call<List<Produk>> call, Response<List<Produk>> response) {
                if (response.isSuccessful()) {
                    mProduk = response.body();
                    updateUI(mProduk);
                }
            }

            @Override
            public void onFailure(Call<List<Produk>> call, Throwable t) {
                Toast.makeText(context, "error when using retrofit", Toast.LENGTH_SHORT).show();
                Log.e("retrofit", t.toString());
            }
        });
    }

    private void updateUI(List<Produk> list) {

        mActionBar.show();
        mLoading.setVisibility(View.GONE);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        //tempat masuk data
        MenuTabAdapter adapter = new MenuTabAdapter(getSupportFragmentManager(), setTitle(), list);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        billing.setOnClickListener(new BillingClicked(this, mProduk));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.waiter) {
            // TODO add notif to kasir here
            Toast.makeText(this, "Waiter sedang dipanggil, silakan tunggu", Toast.LENGTH_SHORT).show();
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

        public BillingClicked(Context mContext, List<Produk> produks) {
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
                Toast.makeText(mContext, "Anda belum memesan apapun", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
