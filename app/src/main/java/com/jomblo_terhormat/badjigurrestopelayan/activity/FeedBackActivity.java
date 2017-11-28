package com.jomblo_terhormat.badjigurrestopelayan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.jomblo_terhormat.badjigurrestopelayan.R;
import com.jomblo_terhormat.badjigurrestopelayan.entity.Produk;
import com.jomblo_terhormat.badjigurrestopelayan.networking.udacity.QueryUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class FeedBackActivity extends AppCompatActivity {

    private RatingBar mRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new submitListener(this, "Terimakasih atas kunjungan Anda", MulaiMenuActivity.class));

        mRatingBar = (RatingBar) findViewById(R.id.ratingnya);
    }

    private class submitListener implements View.OnClickListener {

        private Context mContext;
        private String mToast;
        private Class mClass;


        public submitListener(Context mContext, String mToast, Class mClass) {
            this.mContext = mContext;
            this.mToast = mToast;
            this.mClass = mClass;
        }


        @Override
        public void onClick(View view) {
            new FeedBackAsyncTask().execute(Produk.BASE_PATH + Produk.JSON_FEEDBACK);
            Toast.makeText(mContext, mToast, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(mContext, mClass));


        }
    }


    private class FeedBackAsyncTask extends AsyncTask<String, Void, String> {



        @Override
        protected String doInBackground(String... urls) {

            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            try {
                Log.v("cek", QueryUtils.postWithHttp(QueryUtils.parseStringLinkToURL(urls[0]), createJsonMessage()));
            } catch (IOException e) {
                Log.v("cek", e.getMessage());
            }

            return null;
        }


        @Override
        protected void onPostExecute(String response) {
        }


        private String createJsonMessage() {

            JSONObject jsonObject = new JSONObject();

            try {

                jsonObject.accumulate("no_nota", Produk.NO_NOTA);
                jsonObject.accumulate("rate", Math.round(mRatingBar.getRating() * 2));
                Log.v("cek", Math.round(mRatingBar.getRating() * 2) + "");


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return jsonObject.toString();

        }

    }


}

