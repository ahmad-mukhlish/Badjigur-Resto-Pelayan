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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jomblo_terhormat.badjigurrestopelayan.R;
import com.jomblo_terhormat.badjigurrestopelayan.entity.Produk;
import com.jomblo_terhormat.badjigurrestopelayan.networking.udacity.QueryUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private EditText edUser, edPass;
    private String realUser = "";
    private String realPass = "";
    private String stuser;
    private String stpass;
    private int status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_login);
        edUser = (EditText) findViewById(R.id.edUsername);
        edPass = (EditText) findViewById(R.id.edPassword);
        Button login = (Button) findViewById(R.id.login);
    }

    public void login(View v) {
        stuser = edUser.getText().toString();
        stpass = edPass.getText().toString();
        new LoginAsyncTask(this).execute(Produk.BASE_PATH + Produk.JSON_LOGIN + edUser.getText().toString());
    }


    private class LoginAsyncTask extends AsyncTask<String, Void, String> {


        private Context mContext;

        public LoginAsyncTask(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected String doInBackground(String... urls) {

            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            try {
                JSONArray root = new JSONArray(QueryUtils.fetchResponse(urls[0]));
                if (root.length() == 0) {
                    realPass = "salah";
                } else {
                    realUser = stuser;
                    JSONObject responseObject = root.getJSONObject(0);
                    realPass = responseObject.getString("pass");
                    Produk.no_meja = Integer.parseInt(responseObject.getString("no_meja"));
                    status = Integer.parseInt(responseObject.getString("status"));
                }
            } catch (JSONException e) {
                Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
            }

            return null;
        }


        @Override
        protected void onPostExecute(String response) {

            if (stuser.equals(realUser) && stpass.equals(realPass) && status == 0) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(mContext, "Username atau password tidak sesuai", Toast.LENGTH_SHORT).show();
            }
        }

    }


}
