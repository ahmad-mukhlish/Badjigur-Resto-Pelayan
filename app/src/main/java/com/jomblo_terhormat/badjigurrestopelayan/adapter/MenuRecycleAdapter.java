package com.jomblo_terhormat.badjigurrestopelayan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.jomblo_terhormat.badjigurrestopelayan.R;
import com.jomblo_terhormat.badjigurrestopelayan.activity.DetailActivity;
import com.jomblo_terhormat.badjigurrestopelayan.entity.Produk;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by GOODWARE1 on 9/2/2017.
 */

public class MenuRecycleAdapter extends RecyclerView.Adapter<MenuRecycleAdapter.MenuViewHolder> {

    private Context mContext;
    private List<Produk> mProduks;

    public MenuRecycleAdapter(Context mContext, List<Produk> produks) {
        this.mContext = mContext;
        this.mProduks = produks;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.each_produk_menu, parent, false);
        return new MenuViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MenuViewHolder holder, int position) {
        final Produk currentProduk = mProduks.get(position);

        Picasso.with(mContext).
                load(Produk.BASE_PATH + currentProduk.getPath()).
                into(holder.mGambar);


        holder.mJudul.setText(currentProduk.getNama());
        holder.mTag.setText(currentProduk.getTag());
        holder.mPrice.setText(Produk.formatter("" + currentProduk.getHarga_jual()));
        holder.mQty.setText(currentProduk.getmQty() + "");
        holder.mCart.setChecked(currentProduk.ismCart());

        holder.mPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = currentProduk.getmQty();
                qty++;
                currentProduk.setmQty(qty);
                holder.mQty.setText(qty + "");
                holder.mCart.setChecked(currentProduk.ismCart());

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
                    holder.mCart.setChecked(currentProduk.ismCart());
                }
            }
        });


        holder.mItemView.setOnClickListener(new ProdukListener(position));


    }

    @Override
    public int getItemCount() {
        return mProduks.size();
    }

    class MenuViewHolder extends RecyclerView.ViewHolder {

        ImageView mGambar;
        TextView mJudul, mTag, mPrice, mQty;
        View mItemView;
        Button mPlus, mMinus;
        CheckBox mCart;


        MenuViewHolder(View itemView) {
            super(itemView);
            mGambar = itemView.findViewById(R.id.gambar);
            mJudul = itemView.findViewById(R.id.judul);
            mTag = itemView.findViewById(R.id.tag);
            mPrice = itemView.findViewById(R.id.price);
            mQty = itemView.findViewById(R.id.qty);
            mPlus = itemView.findViewById(R.id.plus);
            mMinus = itemView.findViewById(R.id.minus);
            mCart = itemView.findViewById(R.id.cart);
            mItemView = itemView;
        }


    }

    class ProdukListener implements View.OnClickListener {

        private int position;

        ProdukListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            Produk clickedProduk = mProduks.get(position);
            Intent intent = new Intent(mContext, DetailActivity.class);
            intent.putExtra("gambar", clickedProduk.getPath());
            intent.putExtra("judul", clickedProduk.getNama());
            intent.putExtra("deskripsi", clickedProduk.getDeskripsi());
            intent.putExtra("harga", clickedProduk.getHarga_jual());
            view.getContext().startActivity(intent);
        }
    }


}