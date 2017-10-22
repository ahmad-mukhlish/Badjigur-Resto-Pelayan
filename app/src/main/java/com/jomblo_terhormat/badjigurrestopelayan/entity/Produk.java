package com.jomblo_terhormat.badjigurrestopelayan.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by GOODWARE1 on 10/19/2017.
 */

public class Produk implements Parcelable {

    public static final String DUMMY_JSON = "{" +
            "  \"name\": \"Burger\"," +
            "  \"tag\": \"Fast Food\"," +
            "  \"price\": \"23000\"," +
            "  \"image_path\": \"http://victoriabuzz.com/wp-content/uploads/2016/11/tavern-double-1100x535.jpg\" " +
            "} " ;

    private String mTitle;
    private String mTag;
    private int mPrice;
    private String mImage_path;


    public Produk(String title, String tag,  int vote_average, String image_path) {

        this.mTitle = title;
        this.mTag = tag;
        this.mPrice = vote_average;
        this.mImage_path = image_path;

    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmTag() {
        return mTag;
    }

    public String getmImage_path() {
        return mImage_path;
    }

    public float getmPrice() {
        return mPrice;
    }

    protected Produk(Parcel in) {
        mTitle = in.readString();
        mTag = in.readString();
        mImage_path = in.readString();
        mPrice = in.readInt();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeString(mTag);
        parcel.writeString(mImage_path);
        parcel.writeFloat(mPrice);
    }


}
