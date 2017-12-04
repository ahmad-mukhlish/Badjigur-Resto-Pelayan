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
import com.jomblo_terhormat.badjigurrestopelayan.adapter.CartRecycleAdapter;
import com.jomblo_terhormat.badjigurrestopelayan.entity.Produk;
import com.jomblo_terhormat.badjigurrestopelayan.networking.QueryUtils;

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

public class BillingActivity extends AppCompatActivity {
    private final String LOG_TAG = BillingActivity.class.getName();

    List<Produk> mOrders;
    String mKeterangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);

        mOrders = new ArrayList<>();
        //TODO get mOrders from database

        CartRecycleAdapter billingRecycleAdapter =
                new CartRecycleAdapter(this, mOrders);

        RecyclerView recyclerView = findViewById(R.id.rvBilling);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(billingRecycleAdapter);

        TextView sub = findViewById(R.id.sub);
        sub.setText(Produk.formatter("" + hitungSub(mOrders)));

        TextView ppn = findViewById(R.id.ppn);
        ppn.setText(Produk.formatter("" + ((int) (hitungSub(mOrders) * 0.1))));

        TextView grand = findViewById(R.id.grand);
        grand.setText(Produk.formatter("" + (((int) (hitungSub(mOrders) * 0.1)) + hitungSub(mOrders))));

        Button ask = findViewById(R.id.ask);
        ask.setOnClickListener(new askListener(this));

        setTitle(getString(R.string.title_billing) + " " + Produk.NO_MEJA);



    }

    private int hitungSub(List<Produk> produks) {
        int sub = 0;
        for (Produk produk : produks) {
            sub += produk.getmHarga_jual() * produk.getmQty();
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
            new AskForBillAsyncTask().execute(Produk.BASE_PATH + Produk.JSON_NOTA, Produk.BASE_PATH + Produk.JSON_PESAN);
            Toast.makeText(mContext, getString(R.string.toast_billing), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(mContext, FeedBackActivity.class));
        }


    }


    private class AskForBillAsyncTask extends AsyncTask<String, Void, String> {


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

                for (int i = 0; i < mOrders.size(); i++) {
                    JSONObject jsonProduk = new JSONObject();
                    jsonProduk.accumulate("id_makanan", mOrders.get(i).getmIdMakanan());
                    jsonProduk.accumulate("qty", mOrders.get(i).getmQty());

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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(BillingActivity.this, MainActivity.class));
    }


}
