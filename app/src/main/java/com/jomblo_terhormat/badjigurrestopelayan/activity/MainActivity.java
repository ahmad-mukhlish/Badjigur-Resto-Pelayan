package com.jomblo_terhormat.badjigurrestopelayan.activity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jomblo_terhormat.badjigurrestopelayan.R;
import com.jomblo_terhormat.badjigurrestopelayan.adapter.MenuTabAdapter;
import com.jomblo_terhormat.badjigurrestopelayan.entity.Bahan;
import com.jomblo_terhormat.badjigurrestopelayan.entity.Produk;
import com.jomblo_terhormat.badjigurrestopelayan.networking.ProdukLoader;
import com.jomblo_terhormat.badjigurrestopelayan.networking.QueryUtils;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Produk>> {


    private final String LOG_TAG = MainActivity.class.getName();

    public static List<Produk> mProduk;
    public static Button mBilling;

    private Toolbar mToolBar;
    private LinearLayout mLoading;
    private static final int LOADER_ID = 54;
    private Drawer mDrawer;
    public static List<Bahan> mBahan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProduk = null;

        ConnectivityManager mConnectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = mConnectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        LinearLayout error = findViewById(R.id.error);
        error.setVisibility(View.GONE);

        mLoading = findViewById(R.id.loading);


        mToolBar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolBar);
        mToolBar.setVisibility(View.GONE);

        if (isConnected) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADER_ID, null, this);
        } else {
            error.setVisibility(View.VISIBLE);
        }

        mBilling = findViewById(R.id.billing);
        new DrawerBuilder().withActivity(this).build();

        initNavigationDrawer(savedInstanceState);

        setTitle(getString(R.string.title_main) + " " + Produk.NO_MEJA);

    }


    private void initNavigationDrawer(Bundle savedInstanceState) {

        PrimaryDrawerItem aboutUs = new PrimaryDrawerItem().
                withIdentifier(1).
                withName(R.string.drawer_about_us)
                .withIcon(R.mipmap.about_us);


        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withHeader(R.layout.drawer_header)
                .withDrawerGravity(Gravity.LEFT)
                .withSavedInstance(savedInstanceState)
                .withToolbar(mToolBar)
                .withSelectedItem(-1)
                .addDrawerItems(aboutUs, new DividerDrawerItem()
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position) {

                            case 1: {
                                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                                break;
                            }


                        }

                        return true;
                    }
                })
                .build();


        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mDrawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);


    }


    public void dialogueMore(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View rootDialog = LayoutInflater.from(this).inflate(R.layout.dialogue_more, null);

        Button empty = rootDialog.findViewById(R.id.btn_empty);
        empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer();
                mProduk = null;
                Intent intent = new Intent(MainActivity.this, MulaiMenuActivity.class);
                startActivity(intent);
                Toast.makeText(getBaseContext(), R.string.toast_empty, Toast.LENGTH_SHORT).show();
                new LogoutOrEmptyAsyncTask(MainActivity.this, false).
                        execute(Produk.BASE_PATH + Produk.PUT_EMPTY,
                                Produk.BASE_PATH + Produk.PUT_LOGOUT
                        );
            }
        });

        Button logout = rootDialog.findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer();
                mProduk = null;
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                new LogoutOrEmptyAsyncTask(MainActivity.this, true).
                        execute(Produk.BASE_PATH + Produk.PUT_EMPTY,
                                Produk.BASE_PATH + Produk.PUT_LOGOUT
                        );
                finish();
            }
        });

        builder.setView(rootDialog);
        final AlertDialog dialog = builder.create();
        dialog.show();


    }


    private String[] setTitle() {
        String titles[] = new String[MenuTabAdapter.TOTAL_FRAGMENT];
        titles[0] = getString(R.string.tab_food);
        titles[1] = getString(R.string.tab_beverage);
        titles[2] = getString(R.string.tab_desert);

        return titles;
    }


    @Override
    public Loader<List<Produk>> onCreateLoader(int i, Bundle bundle) {
        if (mProduk == null) {
            return new ProdukLoader(this, Produk.BASE_PATH + Produk.GET_MAKANAN);
        } else
            return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Produk>> loader, final List<Produk> produks) {
        if (mProduk == null || mProduk.isEmpty()) {
            mProduk = produks;
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateUI(produks);
            }
        }, 3000);

    }

    @Override
    public void onLoaderReset(Loader<List<Produk>> loader) {

    }

    private void updateUI(List<Produk> list) {

        mToolBar.setVisibility(View.VISIBLE);
        mLoading.setVisibility(View.GONE);
        ViewPager viewPager = findViewById(R.id.viewpager);
        MenuTabAdapter adapter = new MenuTabAdapter(getSupportFragmentManager(), setTitle(), list);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
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


        switch (item.getItemId()) {

            case R.id.id_menu_waiter: {
                // TODO Add Notification To Kasir Here
                Toast.makeText(this, R.string.toast_waiter_called, Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.id_menu_billing: {
                startActivity(new Intent(MainActivity.this, BillingActivity.class));
                break;
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
                Intent intent = new Intent(MainActivity.this, ConfirmActivity.class);
                intent.putExtra("produks", (ArrayList<Produk>) cartedList(mProduks));
                startActivity(intent);
            } else {
                Toast.makeText(mContext, R.string.toast_no_food_order, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen())
            mDrawer.closeDrawer();
        else
            Toast.makeText(this, R.string.toast_main_menu, Toast.LENGTH_SHORT).show();
    }


    private class LogoutOrEmptyAsyncTask extends AsyncTask<String, Void, String> {

        private Context mContext;
        private boolean mLogout;

        LogoutOrEmptyAsyncTask(Context mContext, boolean mLogout) {
            this.mContext = mContext;
            this.mLogout = mLogout;
        }

        @Override
        protected String doInBackground(String... urls) {

            if (urls.length < 1 || urls[0] == null) {
                return null;
            }


            try {
                QueryUtils.putWithHttp(QueryUtils.parseStringLinkToURL(urls[0]),createJsonMessage());
                if (mLogout)
                    QueryUtils.putWithHttp(QueryUtils.parseStringLinkToURL(urls[1]),createJsonMessage());

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(String response) {
            if (mLogout)
                Toast.makeText(mContext, R.string.toast_logout, Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(mContext, R.string.toast_empty_table, Toast.LENGTH_SHORT).show();

        }

        private String createJsonMessage() {

            JSONObject jsonObject = new JSONObject();

            try {

                jsonObject.accumulate("no_meja", Produk.NO_MEJA);


            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error when create JSON message", e);
            }

            return jsonObject.toString();

        }
    }

    public static void countEstimatedPrice() {

        Long estimated = 0L;

        for (Produk produkNow : mProduk) {

            estimated += produkNow.getmHarga_jual() * produkNow.getmQty();

        }

        if (estimated == 0) {
            mBilling.setVisibility(View.GONE);
        } else {
            mBilling.setVisibility(View.VISIBLE);
            mBilling.setText("Estimated Price\t : \t" + Produk.formatter(estimated + ""));

        }

    }



}

