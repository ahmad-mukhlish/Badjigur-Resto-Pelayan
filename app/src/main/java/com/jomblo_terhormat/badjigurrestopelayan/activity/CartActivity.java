package com.jomblo_terhormat.badjigurrestopelayan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jomblo_terhormat.badjigurrestopelayan.R;
import com.jomblo_terhormat.badjigurrestopelayan.adapter.BillingRecycleAdapter;
import com.jomblo_terhormat.badjigurrestopelayan.entity.Produk;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private final String LOG_TAG = CartActivity.class.getName();

    private List<Produk> mProduks;
    private String mCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);

        Bundle bundle = getIntent().getExtras();
        mProduks = bundle.getParcelableArrayList("produks");

        BillingRecycleAdapter billingRecycleAdapter =
                new BillingRecycleAdapter(this, mProduks);

        RecyclerView recyclerView = findViewById(R.id.rvBilling);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(billingRecycleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        TextView sub = findViewById(R.id.sub);
        sub.setText(Produk.formatter("" + hitungSub(mProduks)));

        TextView ppn = findViewById(R.id.ppn);
        ppn.setText(Produk.formatter("" + ((int) (hitungSub(mProduks) * 0.1))));

        TextView grand = findViewById(R.id.grand);
        grand.setText(Produk.formatter("" + (((int) (hitungSub(mProduks) * 0.1)) + hitungSub(mProduks))));

        Button ask = findViewById(R.id.ask);
        ask.setOnClickListener(new askListener(this));





    }

    private int hitungSub(List<Produk> produks) {
        int sub = 0;
        for (Produk produk : produks) {
            sub += produk.getmHarga_jual() * produk.getmQty();
        }
        return sub;
    }


    private class askListener implements View.OnClickListener {

        private Context mContext;

        askListener(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(mContext, getString(R.string.toast_billing), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(mContext, FeedBackActivity.class));
        }


    }

}


