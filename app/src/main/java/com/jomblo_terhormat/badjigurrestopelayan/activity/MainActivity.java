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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jomblo_terhormat.badjigurrestopelayan.R;
import com.jomblo_terhormat.badjigurrestopelayan.adapter.MenuTabAdapter;
import com.jomblo_terhormat.badjigurrestopelayan.entity.Produk;
import com.jomblo_terhormat.badjigurrestopelayan.networking.ProdukLoader;
import com.jomblo_terhormat.badjigurrestopelayan.networking.QueryUtils;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
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

import static com.jomblo_terhormat.badjigurrestopelayan.networking.QueryUtils.fetchResponse;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Produk>> {

    private final String LOG_TAG = MainActivity.class.getName();

    public static List<Produk> mProduk;
    private Toolbar mToolBar;
    private LinearLayout mLoading;
    private static final int LOADER_ID = 54;
    private Button mBilling;
    private String mKeterangan;
    private Drawer mDrawer;


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


        mToolBar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolBar);
        setTitle(getString(R.string.label_table) + " " + Produk.NO_MEJA);


        mToolBar.setVisibility(View.GONE);


        if (isConnected) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADER_ID, null, this);
        } else {
            error.setVisibility(View.VISIBLE);
        }

        mBilling = (Button) findViewById(R.id.billing);
        new DrawerBuilder().withActivity(this).build();

        initNavigationDrawer(savedInstanceState);
    }


    private void initNavigationDrawer(Bundle savedInstanceState) {

        AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.color.colorPrimary)
                .withCompactStyle(false)
                .withSavedInstance(savedInstanceState)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName(getString(R.string.app_name)).
                                withEmail(R.string.drawer_developed_by).
                                withIcon(getResources().getDrawable(R.mipmap.ic_launcher))
                )
                .build();

        PrimaryDrawerItem chefNote = new PrimaryDrawerItem().
                withIdentifier(1).
                withName(R.string.drawer_item_chef_note)
                .withIcon(R.mipmap.chef_note);

        PrimaryDrawerItem emptyTable = new PrimaryDrawerItem().
                withIdentifier(2).
                withName(R.string.drawer_item_empty_the_table)
                .withIcon(R.mipmap.empty_table);

        PrimaryDrawerItem logout = new PrimaryDrawerItem().
                withIdentifier(3).
                withName(R.string.drawer_item_logout)
                .withIcon(R.mipmap.logout);

        PrimaryDrawerItem cart = new PrimaryDrawerItem().
                withIdentifier(3).
                withName(R.string.drawer_cart)
                .withIcon(R.mipmap.cart);


        mDrawer = new DrawerBuilder()
                .withAccountHeader(accountHeader)
                .withActivity(this)
                .withDrawerGravity(Gravity.LEFT)
                .withSavedInstance(savedInstanceState)
                .withToolbar(mToolBar)
                .withSelectedItem(-1)
                .addDrawerItems(cart, chefNote, emptyTable, logout, new DividerDrawerItem()
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position) {

                            case 1: {
                                if (!cartedList(mProduk).isEmpty()) {
                                    mDrawer.closeDrawer();
                                    Intent intent = new Intent(MainActivity.this, BillingActivity.class);
                                    intent.putExtra("produks", (ArrayList<Produk>) cartedList(mProduk));
                                    intent.putExtra("code","see") ;
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getBaseContext(), R.string.toast_no_carted, Toast.LENGTH_SHORT).show();
                                }
                                break;
                            }

                            case 2: {
                                mDrawer.closeDrawer();
                                dialogueKeterangan(mProduk, 0);
                                break;

                            }
                            case 3: {
                                mDrawer.closeDrawer();
                                mProduk = null;
                                Intent intent = new Intent(MainActivity.this, MulaiMenuActivity.class);
                                startActivity(intent);
                                Toast.makeText(getBaseContext(), R.string.toast_empty, Toast.LENGTH_SHORT).show();
                                //TODO notify the database the table is empty now
                                break;

                            }

                            case 4: {
                                mDrawer.closeDrawer();
                                mProduk = null;
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                                new LogoutAsyncTask(MainActivity.this).
                                        execute(Produk.BASE_PATH + Produk.JSON_LOGOUT + Produk.NO_MEJA);
                                finish();
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

        mToolBar.setVisibility(View.VISIBLE);
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


        switch (item.getItemId()) {

            case R.id.waiter: {
                // TODO add notif to kasir here
                Toast.makeText(this, R.string.toast_waiter_called, Toast.LENGTH_SHORT).show();
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
                dialogueKeterangan(mProduks, 1);
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
            Toast.makeText(mContext, R.string.toast_logout, Toast.LENGTH_SHORT).show();
        }
    }

    private void dialogueKeterangan(final List<Produk> produks, int kode) {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View rootDialog = LayoutInflater.from(this).inflate(R.layout.dialogue_keterangan, null);
        final EditText keterangan = rootDialog.findViewById(R.id.keterangan);
        keterangan.setText(mKeterangan);
        keterangan.setSelection(keterangan.getText().length());

        builder.setView(rootDialog);
        final AlertDialog dialog = builder.create();
        dialog.show();


        TextView ok = rootDialog.findViewById(R.id.ok);
        if (kode == 1) {
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    mKeterangan = keterangan.getText().toString();
                    Intent intent = new Intent(MainActivity.this, BillingActivity.class);
                    intent.putExtra("produks", (ArrayList<Produk>) cartedList(produks));
                    intent.putExtra("code","order") ;
                    new PesanAsyncTask(cartedList(produks)).execute(Produk.BASE_PATH + Produk.JSON_NOTA, Produk.BASE_PATH + Produk.JSON_PESAN);
                    startActivity(intent);
                }
            });
        } else {
            ok.setText(R.string.button_edit_chef);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    mKeterangan = keterangan.getText().toString();
                }
            });
        }

    }


    private class PesanAsyncTask extends AsyncTask<String, Void, String> {


        private List<Produk> mProduks;

        private PesanAsyncTask(List<Produk> mProduks) {
            this.mProduks = mProduks;
        }

        @Override
        protected String doInBackground(String... urls) {

            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            try {
                Produk.NO_NOTA = Integer.parseInt(new JSONObject(fetchResponse(urls[0])).getString("nota"));
                Log.v(LOG_TAG, QueryUtils.postWithHttp(QueryUtils.parseStringLinkToURL(urls[1]), createJsonMessage()));
            } catch (IOException | JSONException e) {
                Log.v(LOG_TAG, "Error when send billing", e);
            }

            return null;
        }


        @Override
        protected void onPostExecute(String response) {

        }


        private String createJsonMessage() {

            JSONObject jsonObject = new JSONObject();

            try {

                JSONArray jsonArray = new JSONArray();

                for (int i = 0; i < mProduks.size(); i++) {
                    JSONObject jsonProduk = new JSONObject();
                    jsonProduk.accumulate("id_makanan", mProduks.get(i).getmIdMakanan());
                    jsonProduk.accumulate("qty", mProduks.get(i).getmQty());

                    jsonArray.put(i, jsonProduk);

                }


                jsonObject.accumulate("meja", Produk.NO_MEJA);
                jsonObject.accumulate("no_nota", Produk.NO_NOTA);
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                String date = df.format(Calendar.getInstance().getTime());

                jsonObject.accumulate("tanggal", date);
                jsonObject.accumulate("catatan", mKeterangan);
                jsonObject.accumulate("pesanan", jsonArray);


            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error when create JSON message", e);
            }

            return jsonObject.toString();

        }

    }

}

