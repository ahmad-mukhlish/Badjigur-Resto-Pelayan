package com.jomblo_terhormat.badjigurrestopelayan.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jomblo_terhormat.badjigurrestopelayan.entity.Produk;
import com.jomblo_terhormat.badjigurrestopelayan.fragment.MenuProdukFragment;

import java.util.ArrayList;
import java.util.List;

public class MenuTabAdapter extends FragmentStatePagerAdapter {

    private final String LOG_TAG = MenuTabAdapter.class.getName();

    public static final int TOTAL_FRAGMENT = 3;
    private String[] mTitles;
    private List<Produk> mLists;

    public MenuTabAdapter(FragmentManager fm, String[] titles, List<Produk> List) {
        super(fm);
        this.mTitles = titles;
        this.mLists = List;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new MenuProdukFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("key", (ArrayList<Produk>) divideProduksForFragment(mLists, position));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public int getCount() {
        return TOTAL_FRAGMENT;
    }

    private List<Produk> divideProduksForFragment(List<Produk> all, int index) {
        List<Produk> result = new ArrayList<Produk>();

        for (Produk produkNow : all) {
            if (produkNow.getmJenis() == index) {
                result.add(produkNow);
            }
        }

        return result;
    }
}
