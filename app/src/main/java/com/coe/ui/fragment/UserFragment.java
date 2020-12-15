package com.coe.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.coe.R;
import com.coe.adapter.CursoAdapter;
import com.coe.adapter.CursosEmAndamentoAdapter;
import com.coe.bean.Aula;
import com.coe.bean.Conquista;
import com.coe.bean.Curso;
import com.coe.bean.Matricula;
import com.coe.bo.ConquistaBo;
import com.coe.bo.CursoBo;
import com.coe.bo.MatriculaBo;
import com.coe.bo.UsuarioBo;
import com.coe.ui.ListaAulaCursoActivity;
import com.coe.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import mobi.stos.httplib.HttpAsync;
import mobi.stos.httplib.inter.FutureCallback;


public class UserFragment extends Fragment {

    private TextView mNomeUser, mTotalAndamento, mTotalConcluido;
    private ListView listView;
    private SharedPreferences.Editor sharedPreferences;
    private List<Aula> aulas;
    private List<Matricula> matriculas;
    private CursosEmAndamentoAdapter cursosEmAndamentoAdapter;
    private CursoBo cursoBo;
    private ConquistaBo conquistaBo;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user, container, false);

        mNomeUser = root.findViewById(R.id.nome_user);
        mTotalAndamento = root.findViewById(R.id.total_andamento);
        mTotalConcluido = root.findViewById(R.id.total_concluido);
        listView = root.findViewById(R.id.list_view_fragment_user);

        cursoBo = new CursoBo(getContext());
        conquistaBo = new ConquistaBo(getContext());
        mNomeUser.setText(new UsuarioBo(getContext()).autenticado().getNome());
        matriculas = new ArrayList<>();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.PREF, Context.MODE_PRIVATE);
        String totalConcluido = String.valueOf(sharedPreferences.getInt(Constants.CONCLUIDO, 0));
        String totalAndamento = String.valueOf(sharedPreferences.getInt(Constants.ANDAMENTO, 0));
        mTotalAndamento.setText("Em andamento: " + totalAndamento);
        mTotalConcluido.setText("Conquistas: " + totalConcluido);


        return root;
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e("RESUME", "onResume:################################# " );
        sharedPreferences = getContext().getSharedPreferences(Constants.PREF, Context.MODE_PRIVATE).edit();
        sharedPreferences.clear();
        sharedPreferences.commit();



        getListCursoMAtriculado();
    }

    private void getListCursoMAtriculado() {

        try {
            HttpAsync httpAsync = new HttpAsync(new URL(getString(R.string.url_base) + "usuarios/" + new UsuarioBo(getContext()).autenticado().getId() + "/matriculas?aprovado=false"));
            httpAsync.setDebug(true);
            httpAsync.get(new FutureCallback() {
                @Override
                public void onBeforeExecute() {

                }

                @Override
                public void onAfterExecute() {
                    getListCursoFinalizado();
                }

                @Override
                public void onSuccess(int responseCode, Object object) {

                    if (responseCode == 200) {
                        matriculas.clear();
                        try {
                            JSONArray jsonArray = (JSONArray) object;
                            JSONObject jsonObject;


                            if (jsonArray.length() > 0) {

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonObject = jsonArray.getJSONObject(i);

                                    Matricula matricula = new Matricula();
                                    matricula.setId(jsonObject.getString("_id"));
                                    matricula.setUsuarioId(jsonObject.getString("usuario"));
                                    matricula.setCursoId(jsonObject.getJSONObject("curso").getString("_id"));
                                    matricula.setNome(jsonObject.getJSONObject("curso").getString("nome"));
                                    matricula.setDescricao(jsonObject.getJSONObject("curso").getString("descricao"));
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
                                    matricula.setDataMatricula(jsonObject.getString("dataDeMatricula"));
                                    matriculas.add(matricula);

                                }

                                updateAdapter(matriculas);

                                int andamento = jsonArray.length();
                                sharedPreferences.putInt(Constants.ANDAMENTO, andamento);
                                sharedPreferences.apply();
                                SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.PREF, Context.MODE_PRIVATE);
                                String totalAndamento = String.valueOf(sharedPreferences.getInt(Constants.ANDAMENTO, 0));
                                mTotalAndamento.setText("Em andamento: " + totalAndamento);

                                Log.e("JSONARRAY", "LENGHT " + jsonArray.length());
                            }else{
                                Log.e("JSONARRAY", "ELSE " + jsonArray.length());
                                int andamento = jsonArray.length();
                                sharedPreferences.putInt(Constants.ANDAMENTO, andamento);
                                sharedPreferences.apply();
                                SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.PREF, Context.MODE_PRIVATE);
                                String totalAndamento = String.valueOf(sharedPreferences.getInt(Constants.ANDAMENTO, 0));
                                mTotalAndamento.setText("Em andamento: " + totalAndamento);

                                updateAdapter(matriculas);
                            }

                        } catch (JSONException e) {
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

    private void getListCursoFinalizado() {

        try {
            HttpAsync httpAsync = new HttpAsync(new URL(getString(R.string.url_base) + "usuarios/" + new UsuarioBo(getContext()).autenticado().getId() + "/matriculas?aprovado=true"));
            httpAsync.setDebug(true);
            httpAsync.get(new FutureCallback() {
                @Override
                public void onBeforeExecute() {

                }

                @Override
                public void onAfterExecute() {

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

                                SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.PREF, Context.MODE_PRIVATE);
                                String totalConcluido = String.valueOf(sharedPreferences.getInt(Constants.CONCLUIDO, 0));
                                mTotalConcluido.setText("Conquistas: " + totalConcluido);

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

    private void updateAdapter(List matricula) {
        cursosEmAndamentoAdapter = new CursosEmAndamentoAdapter(getContext(), matricula);
        listView.setDivider(null);
        listView.setAdapter(cursosEmAndamentoAdapter);
        cursosEmAndamentoAdapter.notifyDataSetChanged();
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Matricula matriculaSelecionada = cursosEmAndamentoAdapter.getItem(position);
            Curso cursoSelecionado = null;
            for (Curso curso : cursoBo.list()) {
                if(curso.getId().equals(matriculaSelecionada.getCursoId())){
                    cursoSelecionado = curso;
                }
            }
            startActivity(new Intent(getContext(), ListaAulaCursoActivity.class).putExtra(Curso.class.getSimpleName(), cursoSelecionado).putExtra("LIST", (Serializable) matriculas));

        });
    }


}
