package com.jomblo_terhormat.badjigurrestopelayan.activiy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.jomblo_terhormat.badjigurrestopelayan.R;
import com.jomblo_terhormat.badjigurrestopelayan.adapter.BillingRecycleAdapter;
import com.jomblo_terhormat.badjigurrestopelayan.entity.Produk;

import java.util.List;

public class BillingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);

        Bundle bundle = getIntent().getExtras();
        List<Produk> produks = bundle.getParcelableArrayList("produks");

        BillingRecycleAdapter billingRecycleAdapter =
                new BillingRecycleAdapter(this, produks);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvBilling);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(billingRecycleAdapter);

        TextView sub = (TextView) findViewById(R.id.sub);
        sub.setText(Produk.formatter("" + hitungSub(produks)));

        TextView ppn = (TextView) findViewById(R.id.ppn);
        ppn.setText(Produk.formatter("" + ((int) (hitungSub(produks) * 0.1))));

        TextView grand = (TextView) findViewById(R.id.grand);
        grand.setText(Produk.formatter("" + (((int) (hitungSub(produks) * 0.1)) + hitungSub(produks))));


    }

    private int hitungSub(List<Produk> produks) {

        int sub = 0;

        for (Produk produk : produks) {

            sub += produk.getHarga_jual() * produk.getmQty();

        }

        return sub;
    }


}
