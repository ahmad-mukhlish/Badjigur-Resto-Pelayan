package com.jomblo_terhormat.badjigurrestopelayan.adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jomblo_terhormat.badjigurrestopelayan.R;
import com.jomblo_terhormat.badjigurrestopelayan.activity.MainActivity;
import com.jomblo_terhormat.badjigurrestopelayan.entity.Produk;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MenuRecycleAdapter extends RecyclerView.Adapter<MenuRecycleAdapter.MenuViewHolder> {

    private final String LOG_TAG = MenuRecycleAdapter.class.getName();

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
                load(Produk.BASE_PATH + currentProduk.getmPath()).
                into(holder.mGambar);


        holder.mJudul.setText(currentProduk.getmNama());
        holder.mTag.setText(currentProduk.getmTag());
        holder.mQty.setText(currentProduk.getmQty() + "");

        if (currentProduk.getmQty() > 0) {
            holder.mQtySet.setVisibility(View.VISIBLE);
            holder.mAdd.setVisibility(View.GONE);
        }


        holder.mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mAdd.setVisibility(View.GONE);
                holder.mQtySet.setVisibility(View.VISIBLE);
                currentProduk.setmQty(1);
                holder.mQty.setText(currentProduk.getmQty() + "");
                MainActivity.countEstimatedPrice();
            }
        });

        holder.mPrice.setText(Produk.formatter("" + currentProduk.getmHarga_jual()));

        holder.mPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = currentProduk.getmQty();
                qty++;
                currentProduk.setmQty(qty);
                holder.mQty.setText(qty + "");
                MainActivity.countEstimatedPrice();

            }
        });

        holder.mMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = currentProduk.getmQty();

                if (qty > 1) {
                    qty--;
                    currentProduk.setmQty(qty);
                    holder.mQty.setText(qty + "");
                } else {
                    currentProduk.setmQty(0);
                    holder.mQtySet.setVisibility(View.GONE);
                    holder.mAdd.setVisibility(View.VISIBLE);
                }

                MainActivity.countEstimatedPrice();

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
        Button mPlus, mMinus, mAdd;
        RelativeLayout mQtySet;


        MenuViewHolder(View itemView) {
            super(itemView);
            mGambar = itemView.findViewById(R.id.gambar);
            mJudul = itemView.findViewById(R.id.judul);
            mTag = itemView.findViewById(R.id.tag);
            mPrice = itemView.findViewById(R.id.price);
            mQty = itemView.findViewById(R.id.qty);
            mPlus = itemView.findViewById(R.id.plus);
            mMinus = itemView.findViewById(R.id.minus);
            mAdd = itemView.findViewById(R.id.btn_add_order);
            mQtySet = itemView.findViewById(R.id.qty_set);
            mItemView = itemView;
        }


    }

    private class ProdukListener implements View.OnClickListener {

        private int position;

        ProdukListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {

            dialogueDetail();
        }

        private void dialogueDetail() {

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            View rootDialog = LayoutInflater.from(mContext).inflate(R.layout.dialogue_detail, null);
            Produk clickedProduk = mProduks.get(position);

            final TextView judul = rootDialog.findViewById(R.id.judul);

            TextView deskripsi = rootDialog.findViewById(R.id.deskripsi);
            TextView harga = rootDialog.findViewById(R.id.harga);
            ImageView imageView = rootDialog.findViewById(R.id.gambar);

            final CardView cardView = rootDialog.findViewById(R.id.detail_card);
            cardView.setVisibility(View.GONE);


            final ProgressBar progressBar = rootDialog.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);


            Picasso.with(mContext).
                    load(Produk.BASE_PATH + clickedProduk.getmPath()).
                    error(R.drawable.detail)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                            cardView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError() {
                            progressBar.setVisibility(View.GONE);
                            cardView.setVisibility(View.VISIBLE);
                        }
                    });


            judul.setText(clickedProduk.getmNama());
            judul.setWidth(imageView.getWidth());

            deskripsi.setText(clickedProduk.getmDeskripsi());
            harga.setText(Produk.formatter("" + clickedProduk.getmHarga_jual()));

            builder.setView(rootDialog);
            final AlertDialog dialog = builder.create();
            dialog.show();

            TextView ok = rootDialog.findViewById(R.id.ok);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

        }


    }


}