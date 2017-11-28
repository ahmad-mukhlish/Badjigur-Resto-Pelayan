package com.jomblo_terhormat.badjigurrestopelayan.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jomblo_terhormat.badjigurrestopelayan.R;
import com.jomblo_terhormat.badjigurrestopelayan.adapter.MenuRecycleAdapter;
import com.jomblo_terhormat.badjigurrestopelayan.entity.Produk;

import java.util.List;


public class MenuProdukFragment extends Fragment {

    private final String LOG_TAG = MenuProdukFragment.class.getName();

    public MenuProdukFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.menu_tab_fragment, container, false);

        List<Produk> listProduk = getArguments().getParcelableArrayList("key");
        MenuRecycleAdapter menuRecycleAdapter =
                new MenuRecycleAdapter(getContext(), listProduk);

        RecyclerView recyclerView = rootView.findViewById(R.id.rvItems);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(menuRecycleAdapter);

        return rootView;
    }

}
