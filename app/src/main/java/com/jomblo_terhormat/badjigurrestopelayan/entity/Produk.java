package com.jomblo_terhormat.badjigurrestopelayan.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by GOODWARE1 on 10/19/2017.
 */

public class Produk implements Parcelable {

    public static final String DUMMY_JSON_MAKANAN = "{\"produk\":[" +
            "{\"name\":\"Burger\",\"tag\":\"Fast Food\",\"price\":20000,\"image_path\":\"https://assets.fastcompany.com/image/upload/w_596,c_limit,q_auto:best,f_auto,fl_lossy/wp-cms/uploads/2017/06/i-1-sonic-burger.jpg\"}," +
            "{\"name\":\"Salad\",\"tag\":\"Vegetarian\",\"price\":12000,\"image_path\":\"https://media1.popsugar-assets.com/files/thumbor/Gx1VGi0PtkPaSj0meVDJBKw33FQ/fit-in/500x500/filters:format_auto-!!-:strip_icc-!!-/2016/02/29/902/n/37139775/3d1ad674_edit_img_image_15954515_1456777527/i/Black-Bean-Salad-Avocado-Dressing-Recipe.png\"}," +
            "{\"name\":\"Nasi goreng\",\"tag\":\"Indonesian\",\"price\":20000,\"image_path\":\"http://resepkoki.co/wp-content/uploads/2015/06/nasi-goreng-putih.jpg\"}," +
            "{\"name\":\"Spaghetti\",\"tag\":\"Italian\",\"price\":16000,\"image_path\":\"https://www.cookingclassy.com/wp-content/uploads/2012/11/spaghetti+with+meat+sauce11.jpg\"}," +
            "{\"name\":\"Bakso\",\"tag\":\"Indonesia\",\"price\":14000,\"image_path\":\"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ51GuBuy1F5Q1GTtiIlZMymb69pOj07Hr7wJ6SzjER3tIH_T3t\"}" +
            "]}" ;

    public static final String DUMMY_JASON_MINUMAN = "{\"produk\":" +
            "[{\"name\":\"Bandrek\",\"tag\":\"Indonesian\",\"price\":8000,\"image_path\":\"http://www.tokomesin.com/wp-content/uploads/2016/02/Peluang-Bisnis-Minuman-Bandrek-dan-Analisa-Bisnisnya-tokomesin.jpg\"}," +
            "{\"name\":\"Bajigur\",\"tag\":\"Indonesian\",\"price\":11000,\"image_path\":\"http://seremanis.com/theme/vintage/assets/img/gallery/bajigur.jpg\"}," +
            "{\"name\":\"Moccacino\",\"tag\":\"Italian\",\"price\":12000,\"image_path\":\"https://alexandracvanny03.files.wordpress.com/2014/11/moc.jpg\"}," +
            "{\"name\":\"Cola\",\"tag\":\"Fastfood\",\"price\":13000,\"image_path\":\"https://images.detik.com/community/media/visual/2016/11/14/126c1616-ab86-4d0c-8c21-381bf334af40.jpg?a=1\"}," +
            "{\"name\":\"Tea\",\"tag\":\"Vegetarian\",\"price\":9000,\"image_path\":\"http://equalexchange.coop/sites/default/files/styles/recipe_listing__normal/public/tea-cup-fancy_2011_Gary-Goodman-2746_2100x1400_300_RGB.jpg?itok=sVAr4b10g\"}" +
            "]}" ;


    public static final String DUMMY_JASON_DESSERT = "{\"produk\":[" +
            "{\"name\":\"Wajit\",\"tag\":\"Indonesian\",\"price\":6000,\"image_path\":\"http://1.bp.blogspot.com/-0w4tzM4r4wg/VjxZNvEy67I/AAAAAAAAI9c/qDDNLxvnHdQ/s1600/Wajit.jpg\"}," +
            "{\"name\":\"Ali Agrem\",\"tag\":\"Indonesian\",\"price\":8000,\"image_path\":\"https://ecs12.tokopedia.net/newimg/cache/300/product-1/2014/11/9/255352/255352_b3eb8ed8-676a-11e4-96f5-de5a2523fab8.jpg\"}," +
            "{\"name\":\"Tiramisu\",\"tag\":\"Italian\",\"price\":14000,\"image_path\":\"http://food.fnr.sndimg.com/content/dam/images/food/fullset/2011/2/4/2/RX-FNM_030111-Sugar-Fix-005_s4x3.jpg.rend.hgtvcom.616.462.suffix/1371597326801.jpeg\"}," +
            "{\"name\":\"Cherry pie\",\"tag\":\"Fastfood\",\"price\":11000,\"image_path\":\"http://food.fnr.sndimg.com/content/dam/images/food/fullset/2009/6/15/0/JI_20731_s4x3.jpg.rend.hgtvcom.616.462.suffix/1371589402748.jpeg\"}," +
            "{\"name\":\"Strawberry Chesscake\",\"tag\":\"Vegetarian\",\"price\":16000,\"image_path\":\"http://media.foodnetwork.ca/imageserve/wp-content/uploads/sites/6/2015/04/strawberry-cheesecake-bites/x.jpg\"}" +
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

    public int getmPrice() {
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
