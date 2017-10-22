package com.jomblo_terhormat.badjigurrestopelayan.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by GOODWARE1 on 10/19/2017.
 */

public class Produk implements Parcelable {

    public static final String DUMMY_JSON = "{\"produk\":[" +
            "{\"name\":\"Burger\",\"tag\":\"Fast Food\",\"price\":20000,\"image_path\":\"http://victoriabuzz.com/wp-content/uploads/2016/11/tavern-double-1100x535.jpg\"}," +
            "{\"name\":\"Salad\",\"tag\":\"Vegetarian\",\"price\":12000,\"image_path\":\"http://www.simplyrecipes.com/wp-content/uploads/2016/07/2016-08-12-BLT-Salad-3-600x400.jpg\"}," +
            "{\"name\":\"Nasi goreng\",\"tag\":\"Indonesian\",\"price\":20000,\"image_path\":\"https://selerasa.com/images/nasi/nasi_goreng/Resep-Dan-Cara-Membuat-Nasi-Goreng-Rumahan-Spesial-Enak-Gurih-Simpel-Dan-Praktis.jpg\"}," +
            "{\"name\":\"Spaghetti\",\"tag\":\"Italian\",\"price\":16000,\"image_path\":\"https://www.cookingclassy.com/wp-content/uploads/2012/11/spaghetti+with+meat+sauce11.jpg\"}," +
            "{\"name\":\"Bakso\",\"tag\":\"Indonesia\",\"price\":14000,\"image_path\":\"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ51GuBuy1F5Q1GTtiIlZMymb69pOj07Hr7wJ6SzjER3tIH_T3t\"}" +
            "]}" ;





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
