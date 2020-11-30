package com.coe.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.coe.R;

import java.net.URL;

import mobi.stos.httplib.HttpAsync;
import mobi.stos.httplib.inter.FutureCallback;

public class CadastroActivity extends AppCompatActivity {

    private EditText mNome, mSobreNome, mEmail, mSenha;
    private TextView mFacaLogin;
    private Button mBtnCriarCadastro;
    private String nome, email, senha, sobreNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorGreen));
        initView();
        buttonCriarConta();
        buttonFacaLogin();


    }

    private void buttonCriarConta() {
        mBtnCriarCadastro.setOnClickListener(v -> {
            nome = mNome.getText().toString();
            sobreNome = mSobreNome.getText().toString();
            email = mEmail.getText().toString();
            senha = mSenha.getText().toString();
            cadastrar();
        });
    }

    private void buttonFacaLogin() {
        mFacaLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void cadastrar() {

        try {
            ProgressDialog progressDialog = new ProgressDialog(this);
            HttpAsync httpAsync = new HttpAsync(new URL(getString(R.string.url_base) + "usuarios"));
            httpAsync.addParam("nome", nome);
            httpAsync.addParam("sobrenome", sobreNome);
            httpAsync.addParam("email", email);
            httpAsync.addParam("senha", senha);
            httpAsync.setDebug(true);
            httpAsync.post(new FutureCallback() {
                @Override
                public void onBeforeExecute() {
                    progressDialog.setMessage(getString(R.string.carregando));
                    progressDialog.show();

                }

                @Override
                public void onAfterExecute() {
                    progressDialog.dismiss();
                }

                @Override
                public void onSuccess(int responseCode, Object object) {
                    Toast toast;

                    switch (responseCode) {
                        case 201:
                            toast = Toast.makeText(CadastroActivity.this, getString(R.string.usuario_registrado), Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                            startActivity(new Intent(CadastroActivity.this, LoginActivity.class));

                            break;
                        case 422:
                            toast = Toast.makeText(CadastroActivity.this, getString(R.string.email_ja_cadastrado), Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            break;
                    }


                }

                @Override
                public void onFailure(Exception exception) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        mNome = findViewById(R.id.edit_nome_cadastro);
        mEmail = findViewById(R.id.edit_email_cadastro);
        mSenha = findViewById(R.id.edit_senha_cadastro);
        mSobreNome = findViewById(R.id.edit_sobre_nome);
        mBtnCriarCadastro = findViewById(R.id.btn_criar_conta);
        mFacaLogin = findViewById(R.id.textview_faca_login);
    }
}