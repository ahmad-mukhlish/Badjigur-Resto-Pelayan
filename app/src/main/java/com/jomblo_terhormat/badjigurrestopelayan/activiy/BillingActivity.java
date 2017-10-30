package com.jomblo_terhormat.badjigurrestopelayan.activiy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jomblo_terhormat.badjigurrestopelayan.R;
import com.jomblo_terhormat.badjigurrestopelayan.adapter.BillingRecycleAdapter;
import com.jomblo_terhormat.badjigurrestopelayan.entity.Produk;

import java.util.ArrayList;
import java.util.List;

public class BillingActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.produk_list);


        BillingRecycleAdapter billingRecycleAdapter =
                new BillingRecycleAdapter(this, cartedList(joinList()));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvItems);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(billingRecycleAdapter);
    }


    private List<Produk> joinList () {
        Bundle bundle = getIntent().getExtras();
        return bundle.getParcelableArrayList("produks") ;
    }



    private List<Produk> cartedList(List<Produk> list) {
        List<Produk> carted = new ArrayList<>();
        for (Produk produk : list) {
              if(produk.ismCart())
              carted.add(produk) ;
        }

        return carted;
    }


}
