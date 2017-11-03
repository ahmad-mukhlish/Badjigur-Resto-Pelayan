package com.jomblo_terhormat.badjigurrestopelayan.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by GOODWARE1 on 10/19/2017.
 */

public class Produk implements Parcelable {

    public static final String BASE_PATH = "http://192.168.1.8/restoran/";
    public static final String JSON_REPLY_MENU = "server.php?operasi=view";
    public static final String ADMIN = "admin";
    public static final String PASSWORD = "jasuke";


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

    @SerializedName("id_makanan")
    private int id_makanan;
    private String nama;
    private int jenis;
    private String tag;
    private String deskripsi;
    private int harga_beli;
    private int harga_jual;
    private String path;

    //field tambahan
    private int mQty = 0;
    private boolean mCart = false;


    public Produk(int id, String nama, int jenis, String tag, String deskripsi, int harga_beli, int harga_jual, String path) {
        this.id_makanan = id;
        this.nama = nama;
        this.jenis = jenis;
        this.tag = tag;
        this.deskripsi = deskripsi;
        this.harga_beli = harga_beli;
        this.harga_jual = harga_jual;
        this.path = path;
    }

    public Produk(String nama, int harga_jual) {
        this.nama = nama;
        this.harga_jual = harga_jual;
    }

    protected Produk(Parcel in) {
        id_makanan = in.readInt();
        nama = in.readString();
        tag = in.readString();
        deskripsi = in.readString();
        harga_beli = in.readInt();
        harga_jual = in.readInt();
        path = in.readString();
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

    public String getNama() {
        return nama;
    }

    public String getTag() {
        return tag;
    }

    public String getPath() {
        return path;
    }

    public int getHarga_jual() {
        return harga_jual;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public int getHarga_beli() {
        return harga_beli;
    }

    public int getJenis() {
        return jenis;
    }

    @Override

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id_makanan);
        parcel.writeString(nama);
        parcel.writeString(tag);
        parcel.writeString(deskripsi);
        parcel.writeInt(harga_beli);
        parcel.writeInt(harga_jual);
        parcel.writeString(path);
        parcel.writeInt(mQty);
        parcel.writeByte((byte) (mCart ? 1 : 0));
    }
}
