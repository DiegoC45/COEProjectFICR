package com.coe.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.coe.R;
import com.coe.bean.Usuario;
import com.coe.bo.UsuarioBo;

import org.json.JSONObject;

import java.net.URL;

import mobi.stos.httplib.HttpAsync;
import mobi.stos.httplib.inter.FutureCallback;

public class LoginActivity extends AppCompatActivity {


    private final static int IMAGE = 1;

    private EditText mEditLogin, mEditSenha;
    private TextView mBtnCadastrar, mEsqueceuSenha;
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

        String a = "111111";
        for (char b:a.toCharArray()) {
            System.out.println("b :" + b);
            if(a.charAt(0)!=b) {
                System.out.println("Diferente");
            }else{
                System.out.println("Não diferente");
            }
        }

        mEsqueceuSenha.setOnClickListener(v -> {
//            startActivity(new Intent(this, CursoVideoYoutubeActivity.class));
//            startActivity(new Intent(this, ListaAulaCursoActivity.class));
//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.setType("image/*");
//            startActivityForResult(intent, IMAGE);
            Toast.makeText(this, "Em desenvolvimento", Toast.LENGTH_LONG).show();

        });

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.e("resquest", "onActivityResult: " + requestCode );
//        if(requestCode == IMAGE){
//            Log.e("result", "onActivityResult: " + resultCode );
//            if(resultCode == RESULT_OK){
//                Log.e("DATA", "onActivityResult: " + data.getData().toString() );
//
//                URI imagemSelecionada = data.getData()
//            }
//        }
//    }

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
        mEsqueceuSenha = findViewById(R.id.esqueceu_senha);
        mBtnCadastrar = findViewById(R.id.button_cadasttrar_se);

    }

    private void login() {

        try {
            ProgressDialog progressDialog = new ProgressDialog(this);
            HttpAsync httpAsync = new HttpAsync(new URL(getString(R.string.url_base) + "login"));
            httpAsync.addParam("email", login);
            httpAsync.addParam("senha", senha);
//            httpAsync.addParam("email", "alex@gmail.com");
//            httpAsync.addParam("senha", "123");
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