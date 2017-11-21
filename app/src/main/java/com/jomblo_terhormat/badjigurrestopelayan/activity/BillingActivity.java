package com.jomblo_terhormat.badjigurrestopelayan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.List;

public class BillingActivity extends AppCompatActivity {

    String mKeterangan;

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

        Button ask = (Button) findViewById(R.id.ask);
        ask.setOnClickListener(new IntentToast(this, "Billing Anda sedang disiapkan, silakan tunggu", FeedBackActivity.class));


    }

    private int hitungSub(List<Produk> produks) {
        int sub = 0;
        for (Produk produk : produks) {
            sub += produk.getHarga_jual() * produk.getmQty();
        }
        return sub;
    }

    private class IntentToast implements View.OnClickListener {

        private Context mContext;
        private String mToast;
        private Class mClass;


        public IntentToast(Context mContext, String mToast, Class mClass) {
            this.mContext = mContext;
            this.mToast = mToast;
            this.mClass = mClass;
        }

        @Override
        public void onClick(View view) {

            Toast.makeText(mContext, mToast, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(mContext, mClass));
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
