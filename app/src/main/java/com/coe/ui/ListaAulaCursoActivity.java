package com.coe.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.coe.R;
import com.coe.adapter.ListaAulaAdapter;
import com.coe.bean.Aula;
import com.coe.bean.Conquista;
import com.coe.bean.Curso;
import com.coe.bean.ListaAula;
import com.coe.bean.Matricula;
import com.coe.bean.Usuario;
import com.coe.bo.ConquistaBo;
import com.coe.bo.ListaAulaBo;
import com.coe.bo.MatriculaBo;
import com.coe.bo.UsuarioBo;
import com.coe.util.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import mobi.stos.httplib.HttpAsync;
import mobi.stos.httplib.inter.FutureCallback;

public class ListaAulaCursoActivity extends AppCompatActivity {

    private Curso curso;
    private ImageView mImageLogo;
    private Toolbar toolbar;
    private TextView mTitleToobar, mSubTitle, mNomeProfessor;
    private Button mBtnMatricula;
    private ListView listView;
    private List<Matricula> matriculas;
    private List<Aula> aulas;
    private SharedPreferences.Editor sharedPreferences;
    private ListaAulaAdapter listaAulaAdapter;
    private ProgressDialog progressDialog;
    private List<Aula> aulaFinalizada;
    private ConquistaBo conquistaBo;
    private String idMatricula;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_aula_curso);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        progressDialog = new ProgressDialog(this);
        sharedPreferences = getSharedPreferences(Constants.PREF, Context.MODE_PRIVATE).edit();
        initView();


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);

        conquistaBo = new ConquistaBo(this);

        curso = (Curso) getIntent().getSerializableExtra(Curso.class.getSimpleName());
        matriculas = (List<Matricula>) getIntent().getSerializableExtra("LIST");

        for (Matricula matricula: matriculas
             ) {
            if(matricula.getCursoId().equals(curso.getId())){
                idMatricula = matricula.getId();
                Log.e("MATRICULA", "onCreate: " + idMatricula );
            }
        }

        aulaFinalizada = (List<Aula>) getIntent().getSerializableExtra(CursoVideoYoutubeActivity.AULAS);

        mBtnMatricula.setOnClickListener(v -> {
            postMatricula();
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        if (curso != null) {
            mTitleToobar.setText(curso.getNome());
            mSubTitle.setText(curso.getDescricao());
            mNomeProfessor.setText(curso.getBadge());
            Picasso.get().load(curso.getImgUrl()).into(mImageLogo);
        }



        if (matriculas != null) {

//            if(aulaFinalizada != null){
//                Log.e("Videos", "onCreate: != null ");
//                for (Matricula matricula : matriculas) {
//                    if (curso.getId().equals(matricula.getCursoId())) {
//                        mBtnMatricula.setVisibility(View.GONE);
//                        Log.e("TAG", "adapter ++++++++= : ");
//
//                        listaAulaAdapter = new ListaAulaAdapter(this, matricula.getAulas());
//                        listView.setAdapter(listaAulaAdapter);
//                        listView.setOnItemClickListener((parent, view, position, id) -> {
//                            Aula aulaSelecionada = listaAulaAdapter.getItem(position);
//
//                            startActivity(new Intent(this, CursoVideoYoutubeActivity.class).putExtra(Matricula.class.getSimpleName(), matricula).putExtra(Aula.class.getSimpleName(), aulaSelecionada));
//                        });
//                    }
//                }
//            }else{
                Log.e("Videos", "onCreate: == null ");
                for (Matricula matricula : matriculas) {

                    if (curso.getId().equals(matricula.getCursoId())) {
                        mBtnMatricula.setVisibility(View.GONE);
                        Log.e("TAG", "adapter != : ");
                        listaAulaAdapter = new ListaAulaAdapter(this, matricula.getAulas());
                        listView.setAdapter(listaAulaAdapter);
                        listView.setOnItemClickListener((parent, view, position, id) -> {
                            Aula aulaSelecionada = listaAulaAdapter.getItem(position);

                            startActivity(new Intent(this, CursoVideoYoutubeActivity.class).putExtra(Matricula.class.getSimpleName(), matricula).putExtra(Aula.class.getSimpleName(), aulaSelecionada));
                        });
                    } else {
                        Log.e("TAG", "matriculeseeeeeeeeeeeeee: ");
                    }
                }
//            }

            getListCursoMAtriculado();

        }
    }


    private void initView() {

        mImageLogo = findViewById(R.id.image_lista_aula);
        toolbar = findViewById(R.id.toolbar_curso_lista);
        mTitleToobar = findViewById(R.id.title_toolbar);
        mBtnMatricula = findViewById(R.id.btn_matricula);
        listView = findViewById(R.id.list_view_aula_curso);
        mSubTitle = findViewById(R.id.sub_title_aula_lista);
        mNomeProfessor = findViewById(R.id.nome_professor);
    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return true;
    }



    private void postMatricula() {

        try {
            HttpAsync httpAsync = new HttpAsync(new URL(getString(R.string.url_base) + "matriculas"));
            httpAsync.addParam("usuario", new UsuarioBo(ListaAulaCursoActivity.this).autenticado().getId());
            httpAsync.addParam("curso", curso.getId());
            httpAsync.setDebug(true);
            httpAsync.post(new FutureCallback() {
                @Override
                public void onBeforeExecute() {
                    progressDialog.setMessage(getString(R.string.carregando));
                    progressDialog.show();
                }

                @Override
                public void onAfterExecute() {

                }

                @Override
                public void onSuccess(int responseCode, Object object) {

                    if (responseCode == 201) {
                        try {
                            JSONObject jsonObject = (JSONObject) object;
                            mBtnMatricula.setVisibility(View.GONE);
                            String message = jsonObject.getString("message");
                            idMatricula = jsonObject.getJSONObject("data").getString("_id");
                            Toast toast = Toast.makeText(ListaAulaCursoActivity.this, message, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        getListCursoMAtriculado();

                    }else{
                        Toast toast = Toast.makeText(ListaAulaCursoActivity.this, getString(R.string.nao_foi_possivel_efetuar_matricula), Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        progressDialog.dismiss();
                    }

                }


                @Override
                public void onFailure(Exception exception) {
                    progressDialog.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getListCursoMAtriculado() {

        try {
            HttpAsync httpAsync = new HttpAsync(new URL(getString(R.string.url_base) + "usuarios/" + new UsuarioBo(ListaAulaCursoActivity.this).autenticado().getId() + "/matriculas?aprovado=false"));
            httpAsync.setDebug(true);
            httpAsync.get(new FutureCallback() {
                @Override
                public void onBeforeExecute() {

                }

                @Override
                public void onAfterExecute() {

                    getListCursoFinalizado();
                    if (matriculas != null) {
                        Log.e("Matricula", "onCreate: " + matriculas.size());

                        for (Matricula matricula : matriculas) {
                            if (curso.getId().equals(matricula.getCursoId())) {
                                mBtnMatricula.setVisibility(View.GONE);

                                listaAulaAdapter = new ListaAulaAdapter(ListaAulaCursoActivity.this, matricula.getAulas());
                                listView.setAdapter(listaAulaAdapter);
                                listView.setOnItemClickListener((parent, view, position, id) -> {
                                    Aula aulaSelecionada = listaAulaAdapter.getItem(position);
                                aulaSelecionada.toString();
                                    startActivity(new Intent(ListaAulaCursoActivity.this, CursoVideoYoutubeActivity.class).putExtra(Matricula.class.getSimpleName(), matricula).putExtra(Aula.class.getSimpleName(), aulaSelecionada));
                                });
                            } else {

                            }
                        }

                    }

                    progressDialog.dismiss();

                }

                @Override
                public void onSuccess(int responseCode, Object object) {

                    if (responseCode == 200) {
                        try {
                            JSONArray jsonArray = (JSONArray) object;
                            JSONObject jsonObject;
                                matriculas.clear();
                            if (jsonArray.length() > 0) {

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonObject = jsonArray.getJSONObject(i);

                                    Matricula matricula = new Matricula();

                                    matricula.setId(jsonObject.getString("_id"));
                                    matricula.setUsuarioId(jsonObject.getString("usuario"));
                                    matricula.setCursoId(jsonObject.getJSONObject("curso").getString("_id"));
                                    JSONArray jsonArrayAula = jsonObject.getJSONArray("aulas");
                                    aulas = new ArrayList<>();
                                    for (int j = 0; j < jsonArrayAula.length(); j++) {
                                        JSONObject jsonObjectAula = jsonArrayAula.getJSONObject(j);
                                        Aula aula = new Aula();
                                        aula.setId(jsonObjectAula.getString("_id"));
                                        aula.setVideoId(jsonObjectAula.getJSONObject("video").getString("_id"));
                                        aula.setNome(jsonObjectAula.getJSONObject("video").getString("nome"));
                                        aula.setConteudo(jsonObjectAula.getJSONObject("video").getString("conteudo"));
                                        aula.setUrl(jsonObjectAula.getJSONObject("video").getString("url"));
                                        aula.setNumeroOrdem(jsonObjectAula.getJSONObject("video").getInt("nrOrdem"));
                                        aula.setCursoId(jsonObjectAula.getJSONObject("video").getString("curso"));
                                        aula.setVisualizado(jsonObjectAula.getBoolean("visualizado"));
                                        aulas.add(aula);
                                    }


                                    matricula.setAulas(aulas);
                                    Log.e("LIst", "matriculas AUla: " + matricula.getAulas().size());
                                    matricula.setDataMatricula(jsonObject.getString("dataDeMatricula"));
                                    Log.e("LIst", "Data: " + matricula.getDataMatricula());
                                    matriculas.add(matricula);

                                }


                                for (Matricula matricula : matriculas
                                ) {
                                    for (Aula aula : matricula.getAulas()) {
                                        Log.e("LIst", "teste: " + aula.getId());
                                    }

                                }
                                MatriculaBo matriculaBo = new MatriculaBo(ListaAulaCursoActivity.this);
                                matriculaBo.clean();
                                matriculaBo.insert(matriculas);


                                for (Matricula matricula : matriculaBo.list()
                                ) {

                                    Log.e("ID", "BO: " + matricula.getId());
                                    Log.e("ID", "Aulas: " + matricula.getAulas());
//                                Log.e("ID", "BO: " + matricula.getAulas().get(0).getId());
//                                for(Aula aula : matricula.getAulas()){
//                                    Log.e("LIst", "BO: " + aula.getId());
//                                }

                                }
//
//                            Log.e("BO", "onSuccess: " + matriculaBo.list().size() );
//                            Log.e("BO", "onSuccess: " + matriculaBo.list().toString() );
                                int andamento = jsonArray.length();
                                sharedPreferences.putInt(Constants.ANDAMENTO, andamento);
                                sharedPreferences.apply();

                                Log.e("JSONARRAY", "LENGHT " + jsonArray.length());
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }


                @Override
                public void onFailure(Exception exception) {
                    progressDialog.dismiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getListCursoFinalizado() {

        try {
            HttpAsync httpAsync = new HttpAsync(new URL(getString(R.string.url_base) + "usuarios/" + new UsuarioBo(ListaAulaCursoActivity.this).autenticado().getId() + "/matriculas?aprovado=true"));
            httpAsync.setDebug(true);
            httpAsync.get(new FutureCallback() {
                @Override
                public void onBeforeExecute() {

                }

                @Override
                public void onAfterExecute() {

                    if (conquistaBo != null) {
                        Log.e("Conquista", "onAfterExecute: " + conquistaBo.list().size());

                        for (Conquista matricula : conquistaBo.list()) {
                            if (matricula.getId().equals(idMatricula)) {
                                Log.e("Conquista", "forrrrrrrr: " + idMatricula);
                                Log.e("Conquista", "forrrrrrrr: " + matricula.getId());
                                mBtnMatricula.setVisibility(View.VISIBLE);
                                List<Aula> vazia = new ArrayList<>();
                                listaAulaAdapter = new ListaAulaAdapter(ListaAulaCursoActivity.this, vazia);
                                listView.setAdapter(listaAulaAdapter);

                            }
                        }

                    }

                }

                @Override
                public void onSuccess(int responseCode, Object object) {

                    if (responseCode == 200) {
                        try {
                            JSONArray jsonArray = (JSONArray) object;
                            JSONObject jsonObject;

                            if (jsonArray.length() > 0) {

                                List<Conquista> conquistas = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonObject = jsonArray.getJSONObject(i);

                                    Conquista conquista = new Conquista();
                                    conquista.setId(jsonObject.getString("_id"));
                                    conquista.setNome(jsonObject.getJSONObject("curso").getString("badge"));
                                    conquista.setImgUrl(jsonObject.getJSONObject("curso").getString("imgUrl"));
                                    conquistas.add(conquista);
                                }

                                conquistaBo.clean();
                                conquistaBo.insert(conquistas);

                                int andamento = jsonArray.length();
                                sharedPreferences.putInt(Constants.CONCLUIDO, andamento);
                                sharedPreferences.apply();

                                Log.e("JSONARRAY", "LENGHT Filnalixado " + jsonArray.length());

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

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