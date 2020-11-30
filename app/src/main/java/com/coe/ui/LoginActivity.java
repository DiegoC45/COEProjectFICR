package com.coe.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.coe.R;
import com.coe.bean.Usuario;
import com.coe.bo.UsuarioBo;
import com.coe.ui.fragment.HomeFragment;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import mobi.stos.httplib.HttpAsync;
import mobi.stos.httplib.inter.FutureCallback;

public class LoginActivity extends AppCompatActivity {

    private EditText mEditLogin, mEditSenha;
    private TextView mBtnCadastrar;
    private Button mbtnLogar;
    private String login, senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorGreen));
        initView();
        buttonLogar();
        buttonCadastrar();


    }

    private void buttonLogar() {
        mbtnLogar.setOnClickListener(v -> {

            login = mEditLogin.getText().toString();
            senha = mEditSenha.getText().toString();
            login();
//            startActivity(new Intent(this, HomeActivity.class));
        });
    }

    private void buttonCadastrar() {
        mBtnCadastrar.setOnClickListener(v -> {
            startActivity(new Intent(this, CadastroActivity.class));
            finish();
        });

    }


    private void initView() {
        mEditLogin = findViewById(R.id.edit_login);
        mEditSenha = findViewById(R.id.edit_senha);
        mbtnLogar = findViewById(R.id.btnLogar);
        mBtnCadastrar = findViewById(R.id.button_cadasttrar_se);

    }

    private void login() {

        try {
            ProgressDialog progressDialog = new ProgressDialog(this);
            HttpAsync httpAsync = new HttpAsync(new URL(getString(R.string.url_base) + "login"));
            httpAsync.addParam("email", login);
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


                    switch (responseCode) {
                        case 200:
                            try {
                                JSONObject jsonObject = (JSONObject) object;

                                Usuario usuario = new Usuario();
                                usuario.setId(jsonObject.getJSONObject("data").getString("_id"));
                                usuario.setNome(jsonObject.getJSONObject("data").getString("nome"));
                                usuario.setSobreNome(jsonObject.getJSONObject("data").getString("sobrenome"));
                                usuario.setEmail(jsonObject.getJSONObject("data").getString("email"));

                                UsuarioBo usuarioBo = new UsuarioBo(LoginActivity.this);
                                usuarioBo.clean();
                                usuarioBo.insert(usuario);

                                Log.e("Size", "Size " + usuarioBo.list().size() );
                                Log.e("ID", "id " + usuarioBo.autenticado().getId() );
                                Log.e("NOme", "Nome  " + usuarioBo.autenticado().getNome() );
                                Log.e("Sobre", "Sobre nome  " + usuarioBo.autenticado().getSobreNome() );
                                Log.e("Email", "Email  " + usuarioBo.autenticado().getEmail());

                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            break;
                        case 403:
                            Toast toast = Toast.makeText(LoginActivity.this, getString(R.string.usuario_invalido), Toast.LENGTH_LONG);
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
}