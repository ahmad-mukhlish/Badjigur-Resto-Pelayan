package com.jomblo_terhormat.badjigurrestopelayan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jomblo_terhormat.badjigurrestopelayan.R;
import com.jomblo_terhormat.badjigurrestopelayan.entity.Produk;

public class MulaiMenuActivity extends AppCompatActivity {

    private final String LOG_TAG = MulaiMenuActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mulai_menu);
        Button mulai = (Button) findViewById(R.id.mulai);
        mulai.setOnClickListener(new MulaiListener(this));
    }


    private class MulaiListener implements View.OnClickListener {

        private Context mContext;

        MulaiListener(Context mContext) {
            this.mContext = mContext;
        }


        @Override
        public void onClick(View v) {
            Produk.PEMESANAN = 1;
            startActivity(new Intent(mContext, MainActivity.class));

        }
    }


}


