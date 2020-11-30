package com.coe.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.coe.R;

import java.util.ArrayList;
import java.util.List;

public class TelaInicialActivity extends AppCompatActivity {

    private Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorGreen));

        initView();
        buttonLogin();

    }

    private void buttonLogin(){
        btnLogin.setOnClickListener(v -> {
            startActivity(new Intent(this,LoginActivity.class));
        });

    }

    private void initView(){
        btnLogin = findViewById(R.id.button_login);
    }
}