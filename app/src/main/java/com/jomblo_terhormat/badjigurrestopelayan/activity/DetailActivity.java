package com.jomblo_terhormat.badjigurrestopelayan.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jomblo_terhormat.badjigurrestopelayan.R;
import com.jomblo_terhormat.badjigurrestopelayan.entity.Produk;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Bundle bundle = getIntent().getExtras();


        final TextView judul = (TextView) findViewById(R.id.judul);
        TextView deskripsi = (TextView) findViewById(R.id.deskripsi);
        TextView harga = (TextView) findViewById(R.id.harga);
        ImageView imageView = (ImageView) findViewById(R.id.gambar);

        final CardView cardView = (CardView) findViewById(R.id.detail_card);
        cardView.setVisibility(View.GONE);


        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);


        Picasso.with(this).
                load(Produk.BASE_PATH + bundle.getString("gambar")).
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
                        autoDirection(judul, 20);
                    }
                });


        judul.setText(bundle.getString("judul"));
        judul.setWidth(imageView.getWidth());

        deskripsi.setText(bundle.getString("deskripsi"));
        harga.setText(Produk.formatter(""+bundle.getInt("harga")));

    }

    private void autoDirection(View view, int bottom) {

        ViewGroup.MarginLayoutParams marginParams =
                (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();

        marginParams.setMargins(marginParams.leftMargin, marginParams.topMargin,
                marginParams.rightMargin, bottom);

    }


}
