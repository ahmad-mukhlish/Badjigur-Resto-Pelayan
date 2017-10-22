package com.jomblo_terhormat.badjigurrestopelayan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    public void onBindViewHolder(final FilmViewHolder holder, int position) {
        final Produk currentProduk = mProduks.get(position);

        Picasso.with(mContext).
                load(currentProduk.getmImage_path()).
                into(holder.mGambar);


        holder.mJudul.setText(currentProduk.getmTitle());
        holder.mTag.setText(currentProduk.getmTag());
        holder.mPrice.setText("Rp. " + currentProduk.getmPrice());
        holder.mQty.setText(currentProduk.getmQty() + "");

        holder.mPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = currentProduk.getmQty();
                qty++;
                currentProduk.setmQty(qty);
                holder.mQty.setText(qty + "");
            }
        });

        holder.mMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = currentProduk.getmQty();
                if (qty > 0) {
                    qty--;
                    currentProduk.setmQty(qty);
                    holder.mQty.setText(qty + "");
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mProduks.size();
    }

    class FilmViewHolder extends RecyclerView.ViewHolder {

        ImageView mGambar;
        TextView mJudul, mTag, mPrice, mQty;
        View mItemView;
        Button mPlus, mMinus;


        FilmViewHolder(View itemView) {
            super(itemView);
            mGambar = itemView.findViewById(R.id.gambar);
            mJudul = itemView.findViewById(R.id.judul);
            mTag = itemView.findViewById(R.id.tag);
            mPrice = itemView.findViewById(R.id.price);
            mQty = itemView.findViewById(R.id.qty);
            mPlus = itemView.findViewById(R.id.plus);
            mMinus = itemView.findViewById(R.id.minus);
            mItemView = itemView;
        }


    }


}