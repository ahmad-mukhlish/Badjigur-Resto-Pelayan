package com.jomblo_terhormat.badjigurrestopelayan.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.jomblo_terhormat.badjigurrestopelayan.adapter.MenuRecycleAdapter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class Produk implements Parcelable {

    private final String LOG_TAG = Produk.class.getName();


    //BASE PATH
    public static final String BASE_PATH = "http://192.168.1.11/ci-restserver/";

    //GET FOR CI
    public static final String GET_MAKANAN = "index.php/Makanan";
    public static final String GET_GAMBAR = "assets/";
    public static final String GET_PASSLOG = "index.php/Meja/passlog/username/";
    public static final String GET_BAHAN = "index.php/Bahan";
    //belum
    public static final String GET_BILLING = "index.php/Pesanan/index/meja/";
    public static final String GET_NOTA = "index.php/Customer/nota";

    //POST FOR CI
    public static final String POST_PESAN = "index.php/Customer/pesan/pemesanan/";

    //PUT FOR CI AND STILL NOT JSON
    public static final String PUT_LOGOUT = "index.php/meja/logout";
    public static final String PUT_LOGIN = "index.php/meja/login";
    public static final String PUT_EMPTY = "index.php/meja/outofservice";
    public static final String PUT_ACTIVE = "index.php/meja/onservice";
    public static final String PUT_FEEDBACK = "index.php/Customer/feedback";
    public static final String PUT_ASK_BILL = "index.php/Customer/askbill";

    public static int NO_NOTA;
    public static int NO_MEJA;
    public static int PEMESANAN;


    public Produk(int mIdMakanan, int mQty) {
        this.mIdMakanan = mIdMakanan;
        this.mQty = mQty;
    }

    public MenuRecycleAdapter.MenuViewHolder getHolder() {
        return holder;
    }

    public void setHolder(MenuRecycleAdapter.MenuViewHolder holder) {
        this.holder = holder;
    }

    private int mIdMakanan;
    private String mNama;
    private int mJenis;
    private String mTag;
    private String mDeskripsi;
    private int mHarga_jual;
    private String mPath;
    private MenuRecycleAdapter.MenuViewHolder holder ;



    //field tambahan
    private int mQty = 0;
    private boolean mCart = false, mDisabled = false;

    private List<Bahan> mBahans;

    public Produk(int id, String nama, int jenis, String tag, String deskripsi, int harga_jual, String path, List<Bahan> bahans) {
        this.mIdMakanan = id;
        this.mNama = nama;
        this.mJenis = jenis;
        this.mTag = tag;
        this.mDeskripsi = deskripsi;
        this.mHarga_jual = harga_jual;
        this.mPath = path;
        this.mBahans = bahans;
    }


    public Produk(String nama, int harga_jual) {
        this.mNama = nama;
        this.mHarga_jual = harga_jual;
    }


    protected Produk(Parcel in) {
        mIdMakanan = in.readInt();
        mNama = in.readString();
        mJenis = in.readInt();
        mTag = in.readString();
        mDeskripsi = in.readString();
        mHarga_jual = in.readInt();
        mPath = in.readString();
        mQty = in.readInt();
        mCart = in.readByte() != 0;
        mDisabled = in.readByte() != 0;
        mBahans = in.createTypedArrayList(Bahan.CREATOR);
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mIdMakanan);
        dest.writeString(mNama);
        dest.writeInt(mJenis);
        dest.writeString(mTag);
        dest.writeString(mDeskripsi);
        dest.writeInt(mHarga_jual);
        dest.writeString(mPath);
        dest.writeInt(mQty);
        dest.writeByte((byte) (mCart ? 1 : 0));
        dest.writeByte((byte) (mDisabled ? 1 : 0));
        dest.writeTypedList(mBahans);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Produk> CREATOR = new Creator<Produk>() {
        @Override
        public Produk createFromParcel(Parcel in) {
            return new Produk(in);
        }

        @Override
        public Produk[] newArray(int size) {
            return new Produk[size];
        }
    };

    public int getmIdMakanan() {
        return mIdMakanan;
    }


    public boolean ismCart() {
        return (mQty > 0);
    }

    public int getmQty() {
        return mQty;
    }

    public void setmQty(int mQty) {
        this.mQty = mQty;
    }

    public String getmNama() {
        return mNama;
    }

    public String getmTag() {
        return mTag;
    }

    public String getmPath() {
        return mPath;
    }

    public int getmHarga_jual() {
        return mHarga_jual;
    }

    public String getmDeskripsi() {
        return mDeskripsi;
    }

    public int getmJenis() {
        return mJenis;
    }

    public List<Bahan> getmBahans() {
        return mBahans;
    }


    public static String formatter(String input) {
        if (!input.isEmpty()) {
            DecimalFormatSymbols symbol = new DecimalFormatSymbols();
            symbol.setGroupingSeparator('.');

            DecimalFormat format = new DecimalFormat(" Rp ###,###");
            format.setDecimalFormatSymbols(symbol);

            return format.format(Double.parseDouble(input));
        } else {
            return "";
        }

    }

    public boolean ismDisabled() {
        return mDisabled;
    }

    public void setmDisabled(boolean mDisabled) {
        this.mDisabled = mDisabled;
    }

}