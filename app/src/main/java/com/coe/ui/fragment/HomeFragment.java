package com.coe.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;


import com.coe.R;
import com.coe.adapter.CursoAdapter;
import com.coe.bean.Aula;
import com.coe.bean.Conquista;
import com.coe.bean.Curso;
import com.coe.bean.Matricula;
import com.coe.bo.AulaBo;
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


public class HomeFragment extends Fragment {

    private ListView mListView;
    private List<Curso> cursos;
    private CursoAdapter cursoAdapter;
    private CursoBo cursoBo;
    private List<Aula> aulas;
    private List<Matricula> matriculas;
    private SharedPreferences.Editor sharedPreferences;
    private ConquistaBo conquistaBo;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);

        mListView = root.findViewById(R.id.listview_home);


        Log.e("HOME", "onCreateView: " + new UsuarioBo(getContext()).autenticado().getNome());

        cursos = new ArrayList<>();
        cursoBo = new CursoBo(getContext());
        matriculas = new ArrayList<>();
        conquistaBo= new ConquistaBo(getContext());
        matriculas.clear();
        conquistaBo.clean();





        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        sharedPreferences = getContext().getSharedPreferences(Constants.PREF, Context.MODE_PRIVATE).edit();
        sharedPreferences.clear();
        sharedPreferences.commit();
        getListCurso();

//        cursos.add(new Curso("HTML 05", "https://professor-falken.com/wp-content/uploads/2017/07/Como-redirigir-o-redireccionar-a-otra-pagina-web-en-HTML-professor-falken.com_.jpg"));
//        cursos.add(new Curso("CSS", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRu6pcZHFlzLjuFiK4DoFDpdrC8VH9dfqsz2g&usqp=CAU"));


        mListView.setOnItemClickListener((parent, view, position, id) -> {
            Curso cursoSelecionado = cursoAdapter.getItem(position);

            Log.e("Selecionado", "onCreateView: " + cursoSelecionado.getNome());

            startActivity(new Intent(getContext(), ListaAulaCursoActivity.class).putExtra(Curso.class.getSimpleName(), cursoSelecionado).putExtra("LIST", (Serializable) matriculas));

        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_pesquisar, menu);


        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint(getText(R.string.digite_nome_curso));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                cursoAdapter = new CursoAdapter(getContext(), listarCursosByNome(s));
                mListView.setAdapter(cursoAdapter);
                cursoAdapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                cursoAdapter.getFilter().filter(s);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void getListCurso() {

        try {
            HttpAsync httpAsync = new HttpAsync(new URL(getString(R.string.url_base) + "cursos"));
            httpAsync.setDebug(true);
            httpAsync.get(new FutureCallback() {
                @Override
                public void onBeforeExecute() {

                }

                @Override
                public void onAfterExecute() {
                    getListCursoMAtriculado();
                }

                @Override
                public void onSuccess(int responseCode, Object object) {

                    if (responseCode == 200) {
                        try {
                            JSONArray jsonArray = (JSONArray) object;
                            JSONObject jsonObject;

                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);

                                Curso curso = new Curso();
                                curso.setId(jsonObject.getString("_id"));
                                curso.setNome(jsonObject.getString("nome"));
                                curso.setDescricao(jsonObject.getString("descricao"));
                                curso.setBadge(jsonObject.getString("badge"));
                                curso.setCategoria(jsonObject.getString("categoria"));
                                curso.setImgUrl(jsonObject.getString("imgUrl"));
                                cursos.add(curso);

                            }

                            Log.e("BO", "200: " + cursos.size());


                            cursoBo.clean();
                            cursoBo.insert(cursos);

                            Log.e("BO", "200 SIZE: " + cursoBo.list().size());

                            updateAdapter();

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

    private void updateAdapter() {
        cursoAdapter = new CursoAdapter(getContext(), cursoBo.list());
        mListView.setDivider(null);
        mListView.setAdapter(cursoAdapter);
        cursoAdapter.notifyDataSetChanged();
    }

    private List<Curso> listarCursosByNome(String query) {

        List<Curso> cursoCategoria;
        CursoBo cursoBo = new CursoBo(getContext());

        cursoCategoria = cursoBo.list(" replace(replace(replace(replace(replace(replace(replace(replace(\n" +
                "replace(replace(replace( lower(nome), 'á','a'), 'ã','a'), 'â','a'), 'é','e'), 'ê','e'), 'í','i'),\n" +
                "'ó','o') ,'õ','o') ,'ô','o'),'ú','u'), 'ç','c') LIKE ? AND nome = ? COLLATE NOCASE ", new String[]{"%" + query + "%", "1"});

        return cursoCategoria;
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
                                MatriculaBo matriculaBo = new MatriculaBo(getContext());
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
