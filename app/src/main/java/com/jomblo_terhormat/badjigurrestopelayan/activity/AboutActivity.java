package com.jomblo_terhormat.badjigurrestopelayan.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jomblo_terhormat.badjigurrestopelayan.R;
import com.jomblo_terhormat.badjigurrestopelayan.adapter.AboutRecycleAdapter;
import com.jomblo_terhormat.badjigurrestopelayan.entity.Personil;

import java.util.ArrayList;
import java.util.List;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        AboutRecycleAdapter aboutRecycleAdapter =
                new AboutRecycleAdapter(this, initPersonil());

        RecyclerView recyclerView = findViewById(R.id.rvAbout);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(aboutRecycleAdapter);

        setTitle("About Us");
    }

    private List<Personil> initPersonil() {

        List<Personil> personils = new ArrayList<>();

        personils.add(new Personil(R.drawable.hatta, "Mouhamad Hatta H. S.", "System Analyst"));
        personils.add(new Personil(R.drawable.mukhlis, "Ahmad Mukhlis S.", "Android developer"));
        personils.add(new Personil(R.drawable.kevin, "Kevin Arden Islamey", "UI / UX, mockup"));
        personils.add(new Personil(R.drawable.agung, "Moch Agung Gumelar", "Website programmer"));
        personils.add(new Personil(R.drawable.randa, "Randa Kurniadi", "Tester"));
        personils.add(new Personil(R.drawable.geri, "Geri Fitrah R. R.", "Requirement Engineer"));


        return personils;
    }
}
