package com.jomblo_terhormat.badjigurrestopelayan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jomblo_terhormat.badjigurrestopelayan.R;
import com.jomblo_terhormat.badjigurrestopelayan.entity.Produk;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by GOODWARE1 on 9/2/2017.
 */

public class MenuRecycleAdapter extends RecyclerView.Adapter<MenuRecycleAdapter.FilmViewHolder> {

    private Context mContext;
    private List<Produk> mProduks;

    public MenuRecycleAdapter(Context mContext, List<Produk> produks) {
        this.mContext = mContext;
        this.mProduks = produks;
    }

    @Override
    public FilmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.each_produk, parent, false);
        return new FilmViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FilmViewHolder holder, int position) {
        Produk currentProduk = mProduks.get(position);

        Picasso.with(mContext).
                load(currentProduk.getmImage_path()).
                into(holder.mGambar);

        holder.mJudul.setText(currentProduk.getmTitle());
        holder.mItemView.setOnClickListener(new FilmListner(position));

    }

    @Override
    public int getItemCount() {
        return mProduks.size();
    }

    class FilmViewHolder extends RecyclerView.ViewHolder {

        ImageView mGambar;
        TextView mJudul;
        View mItemView;

        FilmViewHolder(View itemView) {
            super(itemView);
            mGambar = itemView.findViewById(R.id.gambar);
            mJudul = itemView.findViewById(R.id.judul);
            mItemView = itemView;

        }


    }

    class FilmListner implements View.OnClickListener {

        private int position;

        FilmListner(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            Produk clickedProduk = mProduks.get(position);
        }
    }


}