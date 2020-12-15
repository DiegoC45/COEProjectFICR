package com.coe.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.coe.R;
import com.coe.bo.UsuarioBo;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        getWindow().setStatusBarColor(getResources().getColor(R.color.colorGreen));
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @Override
    protected void onResume() {
        super.onResume();



        Handler handler = new Handler();
        handler.postDelayed(() -> {

            if(new UsuarioBo(SplashActivity.this).autenticado() != null){
                startActivity(new Intent(this, HomeActivity.class));
            }else{
                startActivity(new Intent(this, TelaInicialActivity.class));
            }



        }, 1500);
    }
}