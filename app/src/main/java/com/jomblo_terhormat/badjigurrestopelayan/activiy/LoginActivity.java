package com.jomblo_terhormat.badjigurrestopelayan.activiy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jomblo_terhormat.badjigurrestopelayan.R;

public class LoginActivity extends AppCompatActivity {
    private EditText edUser, edPass;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_login);
        edUser = (EditText) findViewById(R.id.edUsername);
        edPass = (EditText) findViewById(R.id.edPassword);
        login = (Button) findViewById(R.id.login);
        String stuser = edUser.getText().toString();
        String stpass = edPass.getText().toString();
    }

    public  void login(View v){
        String stuser = edUser.getText().toString();
        String stpass = edPass.getText().toString();

        if(stuser.equals("admin") && stpass.equals("admin")){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish() ;
        }
        else
        {
            Toast.makeText(this,"Username atau password tidak sesuai",Toast.LENGTH_SHORT).show();
        }
    }
}
