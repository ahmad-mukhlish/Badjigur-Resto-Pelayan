package com.jomblo_terhormat.badjigurrestopelayan.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Bahan implements Parcelable{

    private int idBahan, qty;

    public Bahan(int idBahan, int qty) {
        this.idBahan = idBahan;
        this.qty = qty;
    }

    protected Bahan(Parcel in) {
        idBahan = in.readInt();
        qty = in.readInt();
    }

    public static final Creator<Bahan> CREATOR = new Creator<Bahan>() {
        @Override
        public Bahan createFromParcel(Parcel in) {
            return new Bahan(in);
        }

        @Override
        public Bahan[] newArray(int size) {
            return new Bahan[size];
        }
    };

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getIdBahan() {
        return idBahan;
    }

    public int getQty() {
        return qty;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idBahan);
        dest.writeInt(qty);
    }
}
