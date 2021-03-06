package com.jomblo_terhormat.badjigurrestopelayan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.jomblo_terhormat.badjigurrestopelayan.R;
import com.jomblo_terhormat.badjigurrestopelayan.entity.Bahan;
import com.jomblo_terhormat.badjigurrestopelayan.entity.Produk;
import com.jomblo_terhormat.badjigurrestopelayan.networking.QueryUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class MulaiMenuActivity extends AppCompatActivity {

    private final String LOG_TAG = MulaiMenuActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mulai_menu);
        ImageView imageView = findViewById(R.id.gambarbg);
        imageView.setOnTouchListener(new OnSwipeTouchListener(this));
        new BahanAsyncTask().execute(Produk.BASE_PATH + Produk.GET_BAHAN);

    }


    private class OnSwipeTouchListener implements View.OnTouchListener {

        private GestureDetector gestureDetector;
        private Context context;


        OnSwipeTouchListener(Context context) {
            gestureDetector = new GestureDetector(context, new GestureListener());
            this.context = context;
        }

        public boolean onTouch(final View view, final MotionEvent motionEvent) {
            return gestureDetector.onTouchEvent(motionEvent);
        }

        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                onClick();
                return super.onSingleTapUp(e);
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                onDoubleClick();
                return super.onDoubleTap(e);
            }

            @Override
            public void onLongPress(MotionEvent e) {
                onLongClick();
                super.onLongPress(e);
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                        }
                    } else {
                        if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffY > 0) {
                                onSwipeDown();
                            } else {
                                onSwipeUp();
                            }
                        }
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        }

        void onSwipeRight() {
        }

        void onSwipeLeft() {

        }

        void onSwipeUp() {
            new ActiveAsyncTask(getBaseContext()).execute(Produk.BASE_PATH + Produk.PUT_ACTIVE);
            Produk.PEMESANAN = 1;
            startActivity(new Intent(context, MainActivity.class));
        }

        void onSwipeDown() {
        }

        public void onClick() {

        }

        void onDoubleClick() {

        }

        void onLongClick() {

        }
    }

    private class ActiveAsyncTask extends AsyncTask<String, Void, String> {

        private Context mContext;

        ActiveAsyncTask(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected String doInBackground(String... urls) {

            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            try {
                Log.v("cik",urls[0]) ;
                Log.v("cik",createJsonMessage()) ;
                QueryUtils.putWithHttp(QueryUtils.parseStringLinkToURL(urls[0]),createJsonMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(String response) {
            Toast.makeText(mContext, R.string.toast_active, Toast.LENGTH_SHORT).show();
        }

        private String createJsonMessage() {

            JSONObject jsonObject = new JSONObject();

            try {

                jsonObject.accumulate("no_meja", Produk.NO_MEJA);


            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error when create JSON message", e);
            }

            return jsonObject.toString();

        }
    }

    //fetch the mBahan data

    public class BahanAsyncTask extends AsyncTask<String, Void, List<Bahan>> {


        BahanAsyncTask() {
        }


        @Override
        protected List<Bahan> doInBackground(String... urls) {

            if (urls[0] == null || urls.length < 1)
                return null;

            return QueryUtils.fetchBahan(urls[0]);
        }

        @Override
        protected void onPostExecute(List<Bahan> bahans) {
            super.onPostExecute(bahans);
            MainActivity.mBahan = bahans;
        }
    }

}


