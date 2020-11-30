package com.coe.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.coe.R;
import com.coe.adapter.CursoAdapter;
import com.coe.bean.Curso;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private ListView mListView;
    private List<Curso> cursos;
    private CursoAdapter cursoAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mListView = root.findViewById(R.id.listview_home);

        cursos = new ArrayList<>();
        cursos.add(new Curso("TEXTO 01", "https://img.freepik.com/fotos-gratis/neon-sinal-barzinhos-imagem-coquetel_23-2148184280.jpg?size=338&ext=jpg"));
        cursos.add(new Curso("TEXTO 02", "https://img.freepik.com/fotos-gratis/neon-sinal-barzinhos-imagem-coquetel_23-2148184280.jpg?size=338&ext=jpg"));
        cursos.add(new Curso("TEXTO 03", "https://lh3.googleusercontent.com/proxy/HcjNV-0aZ5vRV5w0mZOdwAMcy0kcHOv2oA6sJoVAyM0EeFq3QF5MQIuGPfaGjPQO9976jZfDtaXB3L0yZMDFgcwslTY0F46qfHmb60Oy_VCFZWse2YvLnIDD1giaV60g4xjKSr2drzrF6IufvBURl67Z1Qr-k5k"));
        cursos.add(new Curso("TEXTO 04", "https://lh3.googleusercontent.com/proxy/HcjNV-0aZ5vRV5w0mZOdwAMcy0kcHOv2oA6sJoVAyM0EeFq3QF5MQIuGPfaGjPQO9976jZfDtaXB3L0yZMDFgcwslTY0F46qfHmb60Oy_VCFZWse2YvLnIDD1giaV60g4xjKSr2drzrF6IufvBURl67Z1Qr-k5k"));

        cursoAdapter = new CursoAdapter(getContext(),cursos);
        mListView.setDivider(null);
        mListView.setAdapter(cursoAdapter);

        Log.e("B", "homeeeeeeeeee ");

        return root;
    }




}
