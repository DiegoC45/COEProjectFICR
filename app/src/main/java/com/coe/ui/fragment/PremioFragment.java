package com.coe.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.coe.R;
import com.coe.adapter.ConquistaAdapter;
import com.coe.bean.Conquista;
import com.coe.bo.ConquistaBo;

import java.util.ArrayList;
import java.util.List;


public class PremioFragment extends Fragment {

    private GridView gridConquista;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_premio, container, false);

        gridConquista = root.findViewById(R.id.gridConquista);
        List<Conquista> conquistas;

        conquistas = new ConquistaBo(getContext()).list();


        ConquistaAdapter conquistaAdapter = new ConquistaAdapter(getContext(), conquistas);
        gridConquista.setAdapter(conquistaAdapter);

        Log.e("B", "premiooooooooooooooooooooooo ");


        return root;
    }




}
