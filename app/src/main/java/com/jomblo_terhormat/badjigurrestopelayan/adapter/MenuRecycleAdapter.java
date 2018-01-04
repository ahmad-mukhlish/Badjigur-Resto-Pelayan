package com.jomblo_terhormat.badjigurrestopelayan.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jomblo_terhormat.badjigurrestopelayan.R;
import com.jomblo_terhormat.badjigurrestopelayan.activity.MainActivity;
import com.jomblo_terhormat.badjigurrestopelayan.entity.Bahan;
import com.jomblo_terhormat.badjigurrestopelayan.entity.Produk;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.jomblo_terhormat.badjigurrestopelayan.fragment.MenuProdukFragment.recyclerView;


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

        currentProduk.setHolder(holder);


        holder.mJudul.setText(currentProduk.getmNama());
        holder.mTag.setText(currentProduk.getmTag());
        holder.mQty.setText(currentProduk.getmQty() + "");

        holder.mItemView.setOnClickListener(new ProdukListener(position));

        holder.mOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, R.string.toast_out_stock, Toast.LENGTH_SHORT).show();
            }
        });


        if (!cekDisabled(MainActivity.mBahan, currentProduk.getmBahans())) {
            if (currentProduk.getmQty() > 0) {
                holder.mQtySet.setVisibility(View.VISIBLE);
                holder.mAdd.setVisibility(View.GONE);
            }


            holder.mAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!currentProduk.ismDisabled() && !cekDisabled(MainActivity.mBahan, currentProduk.getmBahans())) {
                        holder.mAdd.setVisibility(View.GONE);
                        holder.mQtySet.setVisibility(View.VISIBLE);
                        currentProduk.setmQty(1);
                        holder.mQty.setText(currentProduk.getmQty() + "");
                        MainActivity.countEstimatedPrice();
                        currentProduk.setmDisabled(minusMbahan(MainActivity.mBahan, currentProduk.getmBahans()));
                    } else if (cekDisabled(MainActivity.mBahan, currentProduk.getmBahans())) {
                        holder.mCard.setCardBackgroundColor(Color.parseColor("#E0E0E0"));
                        holder.mAdd.setVisibility(View.GONE);
                        holder.mQtySet.setVisibility(View.GONE);
                        holder.mOut.setVisibility(View.VISIBLE);
                        holder.mPrice.setVisibility(View.GONE);
                        Toast.makeText(mContext, R.string.toast_max_stock, Toast.LENGTH_SHORT).show();
                    } else {
                        holder.mCard.setCardBackgroundColor(Color.parseColor("#E0E0E0"));
                        holder.mPlus.setBackgroundColor(Color.parseColor("#9E9E9E"));
                        holder.mMinus.setBackgroundColor(Color.parseColor("#9E9E9E"));
                        Toast.makeText(mContext, R.string.toast_max_stock, Toast.LENGTH_SHORT).show();
                    }

                }
            });

            holder.mPrice.setText(Produk.formatter("" + currentProduk.getmHarga_jual()));

            holder.mPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!currentProduk.ismDisabled() && !cekDisabled(MainActivity.mBahan, currentProduk.getmBahans())) {
                        int qty = currentProduk.getmQty();
                        qty++;
                        currentProduk.setmQty(qty);
                        holder.mQty.setText(qty + "");
                        MainActivity.countEstimatedPrice();
                        currentProduk.setmDisabled(minusMbahan(MainActivity.mBahan, currentProduk.getmBahans()));
                        Log.v("cek", MainActivity.mBahan.get(0).getQty() + "");
                        Log.v("cek", currentProduk.ismDisabled() + "");
                    } else {
                        holder.mCard.setCardBackgroundColor(Color.parseColor("#E0E0E0"));
                        holder.mPlus.setBackgroundColor(Color.parseColor("#9E9E9E"));
                        holder.mMinus.setBackgroundColor(Color.parseColor("#9E9E9E"));
                        Toast.makeText(mContext, R.string.toast_max_stock, Toast.LENGTH_SHORT).show();
                    }
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

                    plusMbahan(MainActivity.mBahan, currentProduk.getmBahans());
                    checkDisabledAll();

                    MainActivity.countEstimatedPrice();

                }
            });
        } else {
            holder.mCard.setCardBackgroundColor(Color.parseColor("#E0E0E0"));
            holder.mAdd.setVisibility(View.GONE);
            holder.mOut.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return mProduks.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        ImageView mGambar;
        TextView mJudul, mTag, mPrice, mQty;
        View mItemView;
        Button mPlus, mMinus, mAdd, mOut;
        RelativeLayout mQtySet;
        CardView mCard;


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
            mOut = itemView.findViewById(R.id.btn_out);
            mQtySet = itemView.findViewById(R.id.qty_set);
            mCard = itemView.findViewById(R.id.card);
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

    private Boolean minusMbahan(List<Bahan> bahanUtama, List<Bahan> makananBahan) {

        Boolean disabled = false;

        for (Bahan mBahanNow : bahanUtama) {

            for (Bahan makananBahanNow : makananBahan) {
                if (mBahanNow.getIdBahan() == makananBahanNow.getIdBahan()) {
                    if (mBahanNow.getQty() > makananBahanNow.getQty())
                        //if bahan is enough then subtract
                        mBahanNow.setQty(mBahanNow.getQty() - makananBahanNow.getQty());
                    else if (mBahanNow.getQty() == makananBahanNow.getQty()) {
                        //if bahan is equal subtract then disable it
                        mBahanNow.setQty(mBahanNow.getQty() - makananBahanNow.getQty());
                        disabled = true;
                    } else
                        //if bahan is not enough just disable it
                        disabled = true;
                }
            }
        }

        if (!disabled) {
            MainActivity.mBahan = bahanUtama;
        }

        return disabled;
    }

    private Boolean cekDisabled(List<Bahan> bahanUtama, List<Bahan> makananBahan) {

        Boolean disabled = false;

        for (Bahan mBahanNow : bahanUtama) {

            for (Bahan makananBahanNow : makananBahan) {
                if (mBahanNow.getIdBahan() == makananBahanNow.getIdBahan()) {
                    if (mBahanNow.getQty() < makananBahanNow.getQty())
                        disabled = true;
                }
            }
        }
        return disabled;
    }

    private void plusMbahan(List<Bahan> bahanUtama, List<Bahan> makananBahan) {


        for (Bahan mBahanNow : bahanUtama) {

            for (Bahan makananBahanNow : makananBahan) {
                if (mBahanNow.getIdBahan() == makananBahanNow.getIdBahan()) {
                    mBahanNow.setQty(mBahanNow.getQty() + makananBahanNow.getQty());
                }


                MainActivity.mBahan = bahanUtama;

            }

        }

    }


    private void checkDisabledAll() {

        for (final Produk produkNow : MainActivity.mProduk) {

            if (!(cekDisabled(MainActivity.mBahan, produkNow.getmBahans())) && produkNow.getHolder() != null) {

                produkNow.getHolder().mCard.setCardBackgroundColor(Color.WHITE);
                produkNow.getHolder().mOut.setVisibility(View.GONE);

                if (produkNow.getmQty() > 0) {
                    produkNow.getHolder().mQtySet.setVisibility(View.VISIBLE);
                    produkNow.getHolder().mAdd.setVisibility(View.GONE);
                } else
                    produkNow.getHolder().mAdd.setVisibility(View.VISIBLE);


                produkNow.getHolder().mAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!produkNow.ismDisabled() && !cekDisabled(MainActivity.mBahan, produkNow.getmBahans())) {
                            produkNow.getHolder().mAdd.setVisibility(View.GONE);
                            produkNow.getHolder().mQtySet.setVisibility(View.VISIBLE);
                            produkNow.setmQty(1);
                            produkNow.getHolder().mQty.setText(produkNow.getmQty() + "");
                            MainActivity.countEstimatedPrice();
                            produkNow.setmDisabled(minusMbahan(MainActivity.mBahan, produkNow.getmBahans()));
                        } else if (cekDisabled(MainActivity.mBahan, produkNow.getmBahans())) {
                            produkNow.getHolder().mCard.setCardBackgroundColor(Color.parseColor("#E0E0E0"));
                            produkNow.getHolder().mAdd.setVisibility(View.GONE);
                            produkNow.getHolder().mQtySet.setVisibility(View.GONE);
                            produkNow.getHolder().mOut.setVisibility(View.VISIBLE);
                            produkNow.getHolder().mPrice.setVisibility(View.GONE);
                            Toast.makeText(mContext, R.string.toast_max_stock, Toast.LENGTH_SHORT).show();
                        } else {
                            produkNow.getHolder().mCard.setCardBackgroundColor(Color.parseColor("#E0E0E0"));
                            produkNow.getHolder().mPlus.setBackgroundColor(Color.parseColor("#9E9E9E"));
                            produkNow.getHolder().mMinus.setBackgroundColor(Color.parseColor("#9E9E9E"));
                            Toast.makeText(mContext, R.string.toast_max_stock, Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                produkNow.getHolder().mPrice.setText(Produk.formatter("" + produkNow.getmHarga_jual()));

                produkNow.getHolder().mPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (!produkNow.ismDisabled() && !cekDisabled(MainActivity.mBahan, produkNow.getmBahans())) {
                            int qty = produkNow.getmQty();
                            qty++;
                            produkNow.setmQty(qty);
                            produkNow.getHolder().mQty.setText(qty + "");
                            MainActivity.countEstimatedPrice();
                            produkNow.setmDisabled(minusMbahan(MainActivity.mBahan, produkNow.getmBahans()));
                            Log.v("cek", MainActivity.mBahan.get(0).getQty() + "");
                            Log.v("cek", produkNow.ismDisabled() + "");
                        } else {
                            produkNow.getHolder().mCard.setCardBackgroundColor(Color.parseColor("#E0E0E0"));
                            produkNow.getHolder().mPlus.setBackgroundColor(Color.parseColor("#9E9E9E"));
                            produkNow.getHolder().mMinus.setBackgroundColor(Color.parseColor("#9E9E9E"));
                            Toast.makeText(mContext, R.string.toast_max_stock, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                produkNow.getHolder().mMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int qty = produkNow.getmQty();

                        if (qty > 1) {
                            qty--;
                            produkNow.setmQty(qty);
                            produkNow.getHolder().mQty.setText(qty + "");
                        } else {
                            produkNow.setmQty(0);
                            produkNow.getHolder().mQtySet.setVisibility(View.GONE);
                            produkNow.getHolder().mAdd.setVisibility(View.VISIBLE);

                        }

                        plusMbahan(MainActivity.mBahan, produkNow.getmBahans());
                        checkDisabledAll();

                        MainActivity.countEstimatedPrice();

                    }
                });
            }


        }


    }

}

