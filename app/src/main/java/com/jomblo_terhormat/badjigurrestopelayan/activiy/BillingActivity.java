package com.jomblo_terhormat.badjigurrestopelayan.activiy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jomblo_terhormat.badjigurrestopelayan.R;
import com.jomblo_terhormat.badjigurrestopelayan.adapter.MenuRecycleAdapter;
import com.jomblo_terhormat.badjigurrestopelayan.entity.Produk;

import java.util.ArrayList;
import java.util.List;

public class BillingActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.produk_list);


        MenuRecycleAdapter filmRecycleViewAdapter =
                new MenuRecycleAdapter(this, cartedList(joinList()));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvItems);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(filmRecycleViewAdapter);
    }


    private List<Produk> joinList () {
        Bundle bundle = getIntent().getExtras();

        ArrayList<Produk> makanan = bundle.getParcelableArrayList("makanan") ;
        ArrayList<Produk> minuman = bundle.getParcelableArrayList("minuman") ;
        ArrayList<Produk> dessert = bundle.getParcelableArrayList("dessert") ;



        ArrayList<Produk> produks = new ArrayList<Produk>() ;

        produks.addAll(makanan) ;
        produks.addAll(minuman) ;
        produks.addAll(dessert) ;

        return produks ;
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
