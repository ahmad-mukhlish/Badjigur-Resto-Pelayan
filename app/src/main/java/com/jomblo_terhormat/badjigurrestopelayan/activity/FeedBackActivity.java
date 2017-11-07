package com.jomblo_terhormat.badjigurrestopelayan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jomblo_terhormat.badjigurrestopelayan.R;

public class FeedBackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new IntentToast(this, "Terimakasih atas kunjungan Anda", MainActivity.class));

    }

    private class IntentToast implements View.OnClickListener {

        private Context mContext;
        private String mToast;
        private Class mClass;


        public IntentToast(Context mContext, String mToast, Class mClass) {
            this.mContext = mContext;
            this.mToast = mToast;
            this.mClass = mClass;
        }

        @Override
        public void onClick(View view) {

            Toast.makeText(mContext, mToast, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(mContext, mClass));
        }
    }
}
