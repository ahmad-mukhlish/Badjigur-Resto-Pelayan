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

import java.util.Calendar;
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
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        Produk currentProduk = mProduks.get(position);

        Picasso.with(mContext).
                load(Produk.BASE_PATH + Produk.GET_GAMBAR + currentProduk.getmPath()).
                into(holder.mGambar);

        Log.v("cik", Produk.BASE_PATH + Produk.GET_GAMBAR + currentProduk.getmPath());

        if (currentProduk.getHolder() == null) {
            currentProduk.setHolder(holder);
        }

        holder.mGambar.setOnClickListener(new ProdukListener(position));

        holder.mJudul.setText(currentProduk.getmNama());
        holder.mTag.setText(currentProduk.getmTag());
        holder.mQty.setText(currentProduk.getmQty() + "");

        holder.mOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, R.string.toast_out_stock, Toast.LENGTH_SHORT).show();
            }
        });


        if (!checkDisabled(MainActivity.mBahan, currentProduk.getmBahans())) {
            if (currentProduk.getmQty() > 0) {
                holder.mQtySet.setVisibility(View.VISIBLE);
                holder.mAdd.setVisibility(View.GONE);
            }


            holder.mAdd.setOnClickListener(new AddListener(currentProduk, holder));

            holder.mPrice.setText(Produk.formatter("" + currentProduk.getmHarga_jual()));

            holder.mPlus.setOnClickListener(new PlusListener(currentProduk, holder));
            holder.mMinus.setOnClickListener(new MinusListener(currentProduk, holder));
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
                    load(Produk.BASE_PATH + Produk.GET_GAMBAR + clickedProduk.getmPath()).
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

    private Boolean minusMbahan(List<Bahan> availables, List<Bahan> costs) {

        Boolean disabled = false;

        for (Bahan availNow : availables) {

            for (Bahan costNow : costs) {
                if (availNow.getIdBahan() == costNow.getIdBahan()) {
                    if (availNow.getQty() > costNow.getQty())
                        //if bahan is enough then subtract
                        availNow.setQty(availNow.getQty() - costNow.getQty());
                    else if (availNow.getQty() == costNow.getQty()) {
                        //if bahan is equal subtract then disable it
                        availNow.setQty(availNow.getQty() - costNow.getQty());
                        disabled = true;
                    } else
                        //if bahan is not enough just disable it
                        disabled = true;
                }
            }
        }

        if (!disabled) {
            MainActivity.mBahan = availables;
        }

        return disabled;
    }

    private Boolean checkDisabled(List<Bahan> availables, List<Bahan> costs) {

        Boolean disabled = false;

        for (Bahan availNow : availables) {

            for (Bahan costNow : costs) {
                if (availNow.getIdBahan() == costNow.getIdBahan()) {
                    //if something did not enough then the food should be disabled
                    if (availNow.getQty() < costNow.getQty()
                            || Calendar.getInstance().getTime().after(availNow.getKadaluarsa()))
                        disabled = true;
                }
            }
        }
        return disabled;
    }

    private Boolean plusMbahan(List<Bahan> availables, List<Bahan> costs) {

        //assume the food is disabled
        Boolean disabled = true;

        int enough = 0;

        for (Bahan availableNow : availables) {

            for (Bahan costNow : costs) {
                if (availableNow.getIdBahan() == costNow.getIdBahan()) {
                    //add the available again
                    availableNow.setQty(availableNow.getQty() + costNow.getQty());

                    //if the available is more than cost then it's enough
                    if (availableNow.getQty() >= costNow.getQty()) {
                        enough++;
                    }
                }
                MainActivity.mBahan = availables;
            }
        }

        //if the food is enough then it should not be disabled
        if (enough == costs.size())
            disabled = false;

        return disabled;
    }


    private void checkDisabledAll() {

        //checks wether all of food is disabled or not

        for (Produk produkNow : MainActivity.mProduk) {

            //recheck the disable status
            produkNow.setmDisabled(checkDisabled(MainActivity.mBahan, produkNow.getmBahans()));

            //if the food is not disabled and has holder view, then return it back to normal
            if (!(produkNow.ismDisabled()) && produkNow.getHolder() != null) {

                produkNow.getHolder().mCard.setCardBackgroundColor(Color.WHITE);
                produkNow.getHolder().mOut.setVisibility(View.GONE);

                if (produkNow.getmQty() > 0) {
                    produkNow.getHolder().mQtySet.setVisibility(View.VISIBLE);
                    produkNow.getHolder().mPlus.setBackgroundColor(Color.parseColor("#009688"));
                    produkNow.getHolder().mMinus.setBackgroundColor(Color.parseColor("#009688"));
                    produkNow.getHolder().mAdd.setVisibility(View.GONE);
                } else {
                    produkNow.getHolder().mAdd.setVisibility(View.VISIBLE);
                }

                produkNow.getHolder().mPrice.setText(Produk.formatter("" + produkNow.getmHarga_jual()));
                produkNow.getHolder().mAdd.setOnClickListener(new AddListener(produkNow, produkNow.getHolder()));
                produkNow.getHolder().mPlus.setOnClickListener(new PlusListener(produkNow, produkNow.getHolder()));
                produkNow.getHolder().mMinus.setOnClickListener(new MinusListener(produkNow, produkNow.getHolder()));
            }


        }


    }

    private class AddListener implements View.OnClickListener {

        private Produk mProduk;
        private MenuViewHolder mHolder;

        AddListener(Produk mProduk, MenuViewHolder mHolder) {
            this.mProduk = mProduk;
            this.mHolder = mHolder;
        }

        @Override
        public void onClick(View v) {

            if (!mProduk.ismDisabled() && !checkDisabled(MainActivity.mBahan, mProduk.getmBahans())) {
                mHolder.mAdd.setVisibility(View.GONE);
                mHolder.mQtySet.setVisibility(View.VISIBLE);
                mProduk.setmQty(1);
                mHolder.mQty.setText(mProduk.getmQty() + "");
                MainActivity.countEstimatedPrice();
                mProduk.setmDisabled(minusMbahan(MainActivity.mBahan, mProduk.getmBahans()));
            } else if (checkDisabled(MainActivity.mBahan, mProduk.getmBahans())) {
                mHolder.mCard.setCardBackgroundColor(Color.parseColor("#E0E0E0"));
                mHolder.mAdd.setVisibility(View.GONE);
                mHolder.mQtySet.setVisibility(View.GONE);
                mHolder.mOut.setVisibility(View.VISIBLE);
                mHolder.mPrice.setVisibility(View.GONE);
                Toast.makeText(mContext, R.string.toast_max_stock, Toast.LENGTH_SHORT).show();
            } else {
                mHolder.mCard.setCardBackgroundColor(Color.parseColor("#E0E0E0"));
                mHolder.mPlus.setBackgroundColor(Color.parseColor("#9E9E9E"));
                mHolder.mMinus.setBackgroundColor(Color.parseColor("#9E9E9E"));
                Toast.makeText(mContext, R.string.toast_max_stock, Toast.LENGTH_SHORT).show();
            }


        }
    }

    private class PlusListener implements View.OnClickListener {

        private Produk mProduk;
        private MenuViewHolder mHolder;

        PlusListener(Produk mProduk, MenuViewHolder mHolder) {
            this.mProduk = mProduk;
            this.mHolder = mHolder;
        }

        @Override
        public void onClick(View v) {

            if (!mProduk.ismDisabled() && !checkDisabled(MainActivity.mBahan, mProduk.getmBahans())) {
                int qty = mProduk.getmQty();
                qty++;
                mProduk.setmQty(qty);
                mHolder.mQty.setText(qty + "");
                MainActivity.countEstimatedPrice();
                mProduk.setmDisabled(minusMbahan(MainActivity.mBahan, mProduk.getmBahans()));
            } else {
                mHolder.mCard.setCardBackgroundColor(Color.parseColor("#E0E0E0"));
                mHolder.mPlus.setBackgroundColor(Color.parseColor("#9E9E9E"));
                mHolder.mMinus.setBackgroundColor(Color.parseColor("#9E9E9E"));
                Toast.makeText(mContext, R.string.toast_max_stock, Toast.LENGTH_SHORT).show();
            }


        }
    }

    private class MinusListener implements View.OnClickListener {

        private Produk mProduk;
        private MenuViewHolder mHolder;

        MinusListener(Produk mProduk, MenuViewHolder mHolder) {
            this.mProduk = mProduk;
            this.mHolder = mHolder;
        }

        @Override
        public void onClick(View v) {

            int qty = mProduk.getmQty();

            if (qty > 1) {
                qty--;
                mProduk.setmQty(qty);
                mHolder.mQty.setText(qty + "");
            } else {
                mProduk.setmQty(0);
                mHolder.mQtySet.setVisibility(View.GONE);
                mHolder.mAdd.setVisibility(View.VISIBLE);

            }
            mProduk.setmDisabled(plusMbahan(MainActivity.mBahan, mProduk.getmBahans()));
            checkDisabledAll();
            Log.v("cek", MainActivity.mBahan.get(0).getQty() + "");

            MainActivity.countEstimatedPrice();


        }
    }


}

