package com.jomblo_terhormat.badjigurrestopelayan.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Produk implements Parcelable {

    private final String LOG_TAG = Produk.class.getName();

    public static final String BASE_PATH = "http://192.168.1.3/restoran/";
    public static final String JSON_REPLY_MENU = "server.php?operasi=makanan";
    public static final String JSON_PESAN = "server.php?operasi=pesan&pemesanan=";
    public static final String JSON_NOTA = "server.php?operasi=nota";
    public static final String JSON_FEEDBACK = "server.php?operasi=rate";
    public static final String JSON_LOGIN = "server.php?operasi=get_passlog&username=";
    public static final String JSON_LOGOUT = "server.php?operasi=logout_meja&no_meja=";
    //TODO BILLING SHOULD HAVE LATEST NOTA PARAMETER
    public static final String JSON_BILLING = "server.php?operasi=total_pesanan&meja=";
    public static int NO_NOTA;
    public static int NO_MEJA;
    public static int PEMESANAN;


    public Produk(int mIdMakanan, int mQty) {
        this.mIdMakanan = mIdMakanan;
        this.mQty = mQty;
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

    private int mIdMakanan;
    private String mNama;
    private int mJenis;
    private String mTag;
    private String mDeskripsi;
    private int mHarga_beli;
    private int mHarga_jual;
    private String mPath;

    //field tambahan
    private int mQty = 0;
    private boolean mCart = false;


    public Produk(int id, String nama, int jenis, String tag, String deskripsi, int harga_beli, int harga_jual, String path) {
        this.mIdMakanan = id;
        this.mNama = nama;
        this.mJenis = jenis;
        this.mTag = tag;
        this.mDeskripsi = deskripsi;
        this.mHarga_beli = harga_beli;
        this.mHarga_jual = harga_jual;
        this.mPath = path;
    }

    public Produk(String nama, int harga_jual) {
        this.mNama = nama;
        this.mHarga_jual = harga_jual;
    }

    public int getmIdMakanan() {
        return mIdMakanan;
    }

    protected Produk(Parcel in) {
        mIdMakanan = in.readInt();
        mNama = in.readString();
        mTag = in.readString();
        mDeskripsi = in.readString();
        mHarga_beli = in.readInt();
        mHarga_jual = in.readInt();
        mPath = in.readString();
        mQty = in.readInt();
        mCart = in.readByte() != 0;
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

    @Override

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mIdMakanan);
        parcel.writeString(mNama);
        parcel.writeString(mTag);
        parcel.writeString(mDeskripsi);
        parcel.writeInt(mHarga_beli);
        parcel.writeInt(mHarga_jual);
        parcel.writeString(mPath);
        parcel.writeInt(mQty);
        parcel.writeByte((byte) (mCart ? 1 : 0));
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
}