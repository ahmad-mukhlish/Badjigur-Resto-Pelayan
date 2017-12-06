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
import com.jomblo_terhormat.badjigurrestopelayan.adapter.CartRecycleAdapter;
import com.jomblo_terhormat.badjigurrestopelayan.entity.Produk;
import com.jomblo_terhormat.badjigurrestopelayan.networking.QueryUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.jomblo_terhormat.badjigurrestopelayan.networking.QueryUtils.fetchResponse;

public class CartActivity extends AppCompatActivity {

    private final String LOG_TAG = CartActivity.class.getName();

    List<Produk> mProduks;
    String mKeterangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Bundle bundle = getIntent().getExtras();
        mProduks = bundle.getParcelableArrayList("produks");

        CartRecycleAdapter cartRecycleAdapter =
                new CartRecycleAdapter(this, mProduks);

        RecyclerView recyclerView = findViewById(R.id.rvCart);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(cartRecycleAdapter);

        TextView sub = findViewById(R.id.sub);
        sub.setText(Produk.formatter("" + hitungSub(mProduks)));

        TextView ppn = findViewById(R.id.ppn);
        ppn.setText(Produk.formatter("" + ((int) (hitungSub(mProduks) * 0.1))));

        TextView grand = findViewById(R.id.grand);
        grand.setText(Produk.formatter("" + (((int) (hitungSub(mProduks) * 0.1)) + hitungSub(mProduks))));

        Button order = findViewById(R.id.order);
        order.setOnClickListener(new OrderListener(this));

        setTitle(getString(R.string.title_cart) + " " + Produk.NO_MEJA);

    }

    private int hitungSub(List<Produk> produks) {
        int sub = 0;
        for (Produk produk : produks) {
            sub += produk.getmHarga_jual() * produk.getmQty();
        }
        return sub;
    }


    private class OrderListener implements View.OnClickListener {

        private Context mContext;

        OrderListener(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public void onClick(View view) {
            dialogueKeterangan(1);
        }
    }


    private class OrderAsyncTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... urls) {

            if (urls.length < 1 || urls[0] == null) {
                return null;
            }


            try {

                if (Produk.PEMESANAN == 1) {
                    Produk.NO_NOTA = Integer.parseInt(new JSONObject(fetchResponse(urls[0])).getString("nota"));
                }
                Log.v(LOG_TAG, QueryUtils.postWithHttp(QueryUtils.parseStringLinkToURL(urls[1]), createJsonMessage()));

            } catch (IOException | JSONException e) {
                Log.v(LOG_TAG, "Error when send billing", e);
            }

            return null;
        }


        @Override
        protected void onPostExecute(String response) {
            Produk.PEMESANAN++;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_cart, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.note) {
            dialogueKeterangan(0);
        }

        return super.onOptionsItemSelected(item);
    }

    private void dialogueKeterangan(int kode) {


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
                    new OrderAsyncTask().execute(Produk.BASE_PATH + Produk.JSON_NOTA,
                            Produk.BASE_PATH + Produk.JSON_PESAN + Produk.PEMESANAN);
                    Intent intent = new Intent(CartActivity.this, BillingActivity.class);
                    startActivity(intent);
                    Toast.makeText(getBaseContext(), R.string.toast_order, Toast.LENGTH_SHORT).show();
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

}


