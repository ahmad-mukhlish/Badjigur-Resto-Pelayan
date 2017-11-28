package com.jomblo_terhormat.badjigurrestopelayan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.jomblo_terhormat.badjigurrestopelayan.R;
import com.jomblo_terhormat.badjigurrestopelayan.entity.Produk;
import com.jomblo_terhormat.badjigurrestopelayan.networking.QueryUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private final String LOG_TAG = LoginActivity.class.getName();

    private EditText mEdUser, mEdPass;
    private String mRealUser = "", mRealPass = "";
    private String mStuser, mStpass;
    private int mStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_login);
        mEdUser = (EditText) findViewById(R.id.edUsername);
        mEdPass = (EditText) findViewById(R.id.edPassword);
    }

    public void login(View v) {
        mStuser = mEdUser.getText().toString();
        mStpass = mEdPass.getText().toString();
        new LoginAsyncTask(this).execute(Produk.BASE_PATH + Produk.JSON_LOGIN + mEdUser.getText().toString());
    }


    private class LoginAsyncTask extends AsyncTask<String, Void, String> {


        private Context mContext;

        LoginAsyncTask(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected String doInBackground(String... urls) {

            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            try {
                JSONObject root = new JSONObject(QueryUtils.fetchResponse(urls[0]));
                if (root.length() == 0) {
                    mRealPass = "salah";
                } else {
                    mRealUser = mStuser;
                    mRealPass = root.getString("pass");
                    Produk.NO_MEJA = Integer.parseInt(root.getString("no_meja"));
                    mStatus = Integer.parseInt(root.getString("status"));
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Problem parsing the JSON results", e);
            }

            return null;
        }


        @Override
        protected void onPostExecute(String response) {
            if (mStuser.equals(mRealUser) && mStpass.equals(mRealPass) && mStatus == 0) {
                Intent intent = new Intent(LoginActivity.this, MulaiMenuActivity.class);
                startActivity(intent);
                finish();
            } else if (mStatus == 1 || mStatus == 2) {
                Toast.makeText(mContext, R.string.used, Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(mContext, R.string.password_mismatch, Toast.LENGTH_SHORT).show();
            }
        }

    }


}
