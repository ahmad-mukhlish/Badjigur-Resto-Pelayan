package com.jomblo_terhormat.badjigurrestopelayan.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jomblo_terhormat.badjigurrestopelayan.fragment.MenuProdukFragment;

/**
 * Created by GOODWARE1 on 10/19/2017.
 */

public class MenuTabAdapter extends FragmentStatePagerAdapter {

    public static final int TOTAL_FRAGMENT = 3;
    private String[] titles ;

    public MenuTabAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        this.titles = titles ;
    }



    @Override
    public Fragment getItem(int position) {
        return new MenuProdukFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return TOTAL_FRAGMENT;
    }
}
