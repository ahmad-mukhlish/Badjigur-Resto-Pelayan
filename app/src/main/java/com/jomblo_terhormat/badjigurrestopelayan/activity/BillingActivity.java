package com.jomblo_terhormat.badjigurrestopelayan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jomblo_terhormat.badjigurrestopelayan.R;
import com.jomblo_terhormat.badjigurrestopelayan.adapter.BillingRecycleAdapter;
import com.jomblo_terhormat.badjigurrestopelayan.entity.Produk;
import com.jomblo_terhormat.badjigurrestopelayan.networking.QueryUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BillingActivity extends AppCompatActivity {
    private final String LOG_TAG = BillingActivity.class.getName();

    private List<Produk> mBillings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);

        new BillingAsyncTask().execute(Produk.BASE_PATH + Produk.JSON_BILLING + Produk.NO_MEJA);


    }

    private void updateUI(List<Produk> billings) {
        BillingRecycleAdapter billingRecycleAdapter =
                new BillingRecycleAdapter(this, billings);
        RecyclerView recyclerView = findViewById(R.id.rvBilling);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(billingRecycleAdapter);

        TextView sub = findViewById(R.id.sub);
        sub.setText(Produk.formatter("" + hitungSub(billings)));

        TextView ppn = findViewById(R.id.ppn);
        ppn.setText(Produk.formatter("" + ((int) (hitungSub(billings) * 0.1))));

        TextView grand = findViewById(R.id.grand);
        grand.setText(Produk.formatter("" + (((int) (hitungSub(billings) * 0.1)) + hitungSub(billings))));

        Button ask = findViewById(R.id.ask);
        ask.setOnClickListener(new askListener(this));

        Button add = findViewById(R.id.id_btn_add_billing);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BillingActivity.this, MainActivity.class));

            }
        });

        setTitle(getString(R.string.title_billing) + " " + Produk.NO_MEJA);

    }

    private int hitungSub(List<Produk> produks) {
        int sub = 0;
        for (Produk produk : produks) {
            Log.v("cik", produk.getmHarga_jual() + "");
            sub += produk.getmHarga_jual() * produk.getmQty();
            Log.v("cik", sub + "");

        }
        return sub;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(BillingActivity.this, MainActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class askListener implements View.OnClickListener {

        private Context mContext;

        askListener(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public void onClick(View view) {
            //TODO add ask for bill asyntask here
            new AskForBillAsyncTask().execute();
            Toast.makeText(mContext, getString(R.string.toast_billing), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(mContext, FeedBackActivity.class));
        }


    }


    private class AskForBillAsyncTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... urls) {


            return null;
        }


        @Override
        protected void onPostExecute(String response) {

        }


    }

    private class BillingAsyncTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... urls) {

            List<Produk> billingProduks = new ArrayList<>();

            try {
                JSONArray arrayBill = new JSONArray(QueryUtils.fetchResponse(urls[0]));
                for (int i = 0; i < arrayBill.length(); i++) {
                    JSONObject billingNow = arrayBill.getJSONObject(i);
                    Produk produk = new Produk(billingNow.getInt("id_makanan"), billingNow.getInt("qty"));
                    billingProduks.add(produk);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            createBillingProduks(billingProduks);


            return null;
        }


        @Override
        protected void onPostExecute(String response) {

            updateUI(mBillings);

        }

        private void createBillingProduks(List<Produk> produks) {

            mBillings = MainActivity.mProduk;

            for (Produk produkNow : mBillings) {
                produkNow.setmQty(0);

                for (Produk billingNow : produks) {

                    if (produkNow.getmIdMakanan() == billingNow.getmIdMakanan()) {
                        produkNow.setmQty(billingNow.getmQty());
                    }

                }

            }


        }


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(BillingActivity.this, MainActivity.class));
    }


}
