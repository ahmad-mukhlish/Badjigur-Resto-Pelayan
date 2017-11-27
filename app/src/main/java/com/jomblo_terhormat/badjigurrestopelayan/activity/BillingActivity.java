package com.jomblo_terhormat.badjigurrestopelayan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jomblo_terhormat.badjigurrestopelayan.R;
import com.jomblo_terhormat.badjigurrestopelayan.adapter.BillingRecycleAdapter;
import com.jomblo_terhormat.badjigurrestopelayan.entity.Produk;
import com.jomblo_terhormat.badjigurrestopelayan.networking.udacity.QueryUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.util.Log.v;
import static com.jomblo_terhormat.badjigurrestopelayan.networking.udacity.QueryUtils.fetchResponse;

public class BillingActivity extends AppCompatActivity {

    List<Produk> mProduks;
    String mKeterangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);

        Bundle bundle = getIntent().getExtras();
        mProduks = bundle.getParcelableArrayList("produks");

        BillingRecycleAdapter billingRecycleAdapter =
                new BillingRecycleAdapter(this, mProduks);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvBilling);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(billingRecycleAdapter);

        TextView sub = (TextView) findViewById(R.id.sub);
        sub.setText(Produk.formatter("" + hitungSub(mProduks)));

        TextView ppn = (TextView) findViewById(R.id.ppn);
        ppn.setText(Produk.formatter("" + ((int) (hitungSub(mProduks) * 0.1))));

        TextView grand = (TextView) findViewById(R.id.grand);
        grand.setText(Produk.formatter("" + (((int) (hitungSub(mProduks) * 0.1)) + hitungSub(mProduks))));

        Button ask = (Button) findViewById(R.id.ask);
        ask.setOnClickListener(new askListener(this, "Billing Anda sedang disiapkan, silakan tunggu", FeedBackActivity.class));


    }

    private int hitungSub(List<Produk> produks) {
        int sub = 0;
        for (Produk produk : produks) {
            sub += produk.getHarga_jual() * produk.getmQty();
        }
        return sub;
    }

    private String createJsonMessage() {

        JSONObject jsonObject = new JSONObject();

        try {

            JSONArray jsonArray = new JSONArray();

            for (int i = 0; i < mProduks.size(); i++) {
                JSONObject jsonProduk = new JSONObject();
                jsonProduk.accumulate("id_makanan", mProduks.get(i).getId_makanan());
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
            e.printStackTrace();
        }

        return jsonObject.toString();

    }

    private class askListener implements View.OnClickListener {

        private Context mContext;
        private String mToast;
        private Class mClass;


        public askListener(Context mContext, String mToast, Class mClass) {
            this.mContext = mContext;
            this.mToast = mToast;
            this.mClass = mClass;
        }

        @Override
        public void onClick(View view) {


            new BillingAsyncTask(createJsonMessage()).execute(Produk.BASE_PATH + Produk.JSON_NOTA, Produk.BASE_PATH + Produk.JSON_POST);
            Toast.makeText(mContext, mToast, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(mContext, mClass));
        }


    }


    private class BillingAsyncTask extends AsyncTask<String, Void, String> {


        private String mMessage;

        public BillingAsyncTask(String mMessage) {
            this.mMessage = mMessage;
        }

        @Override
        protected String doInBackground(String... urls) {

            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            try {
                Produk.NO_NOTA = Integer.parseInt(new JSONObject(fetchResponse(urls[0])).getString("nota"));
                Log.v("cek", QueryUtils.postWithHttp(QueryUtils.parseStringLinkToURL(urls[1]), mMessage));
            } catch (IOException e) {
                v("cek", e.getMessage());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(String response) {
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_billing, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.note) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View rootDialog = LayoutInflater.from(this).inflate(R.layout.dialogue_keterangan, null);
            final EditText keterangan = rootDialog.findViewById(R.id.keterangan);
            keterangan.setText(mKeterangan);
            keterangan.setSelection(keterangan.getText().length());

            builder.setView(rootDialog);
            final AlertDialog dialog = builder.create();
            dialog.show();


            TextView ok = rootDialog.findViewById(R.id.ok);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    mKeterangan = keterangan.getText().toString();
                }
            });


        }

        return super.onOptionsItemSelected(item);
    }


}


