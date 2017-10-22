package com.jomblo_terhormat.badjigurrestopelayan.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jomblo_terhormat.badjigurrestopelayan.entity.Produk;
import com.jomblo_terhormat.badjigurrestopelayan.fragment.MenuProdukFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GOODWARE1 on 10/19/2017.
 */

public class MenuTabAdapter extends FragmentStatePagerAdapter {

    public static final int TOTAL_FRAGMENT = 3;
    private String[] mTitles;
    private List<List<Produk>> mLists;

    public MenuTabAdapter(FragmentManager fm, String[] titles, List<List<Produk>> List) {
        super(fm);
        this.mTitles = titles ;
        this.mLists = List ;

    }



    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        fragment = new MenuProdukFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("key", (ArrayList<Produk>) mLists.get(position));
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
}
