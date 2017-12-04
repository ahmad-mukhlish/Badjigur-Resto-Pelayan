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
import com.jomblo_terhormat.badjigurrestopelayan.networking.QueryUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class FeedBackActivity extends AppCompatActivity {

    private final String LOG_TAG = FeedBackActivity.class.getName();

    private RatingBar mRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new submitListener(this));

        mRatingBar = (RatingBar) findViewById(R.id.ratingnya);
        setTitle(getString(R.string.label_table) + " " + Produk.NO_MEJA);

    }

    private class submitListener implements View.OnClickListener {

        private Context mContext;


        submitListener(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public void onClick(View view) {
            new FeedBackAsyncTask().execute(Produk.BASE_PATH + Produk.JSON_FEEDBACK);
            Toast.makeText(mContext, getString(R.string.toast_thank), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(mContext, MulaiMenuActivity.class));


        }
    }


    private class FeedBackAsyncTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... urls) {

            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            try {
                Log.v(LOG_TAG, QueryUtils.postWithHttp(QueryUtils.parseStringLinkToURL(urls[0]), createJsonMessage()));
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error when post with http", e);
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


            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error when create JSON message", e);
            }

            return jsonObject.toString();

        }

    }


}

