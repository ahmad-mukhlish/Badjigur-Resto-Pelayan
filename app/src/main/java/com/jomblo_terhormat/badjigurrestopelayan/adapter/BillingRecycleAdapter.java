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

public class BillingRecycleAdapter extends RecyclerView.Adapter<BillingRecycleAdapter.BillingViewHolder> {

    private Context mContext;
    private List<Produk> mProduks;

    public BillingRecycleAdapter(Context mContext, List<Produk> produks) {
        this.mContext = mContext;
        this.mProduks = produks;
    }

    @Override
    public BillingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.each_produk_billing, parent, false);
        return new BillingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BillingViewHolder holder, int position) {
        final Produk currentProduk = mProduks.get(position);

        Picasso.with(mContext).
                load(Produk.BASE_PATH + currentProduk.getPath()).
                into(holder.mGambar);


        holder.mJudul.setText(currentProduk.getNama());
        holder.mTag.setText(currentProduk.getTag());
        holder.mPrice.setText("Rp. " + (currentProduk.getHarga_jual() * currentProduk.getmQty()));
        holder.mQty.setText(currentProduk.getmQty() + "");



    }

    @Override
    public int getItemCount() {
        return mProduks.size();
    }

    class BillingViewHolder extends RecyclerView.ViewHolder {

        ImageView mGambar;
        TextView mJudul, mTag, mPrice, mQty;
        View mItemView;


        BillingViewHolder(View itemView) {
            super(itemView);
            mGambar = itemView.findViewById(R.id.gambar);
            mJudul = itemView.findViewById(R.id.judul);
            mTag = itemView.findViewById(R.id.tag);
            mPrice = itemView.findViewById(R.id.price);
            mQty = itemView.findViewById(R.id.qty);
            mItemView = itemView;
        }


    }


}