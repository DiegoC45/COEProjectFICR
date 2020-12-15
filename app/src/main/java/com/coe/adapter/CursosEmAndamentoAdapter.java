package com.coe.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.coe.R;
import com.coe.bean.Matricula;

import java.util.List;

public class CursosEmAndamentoAdapter extends ArrayAdapter<Matricula> {


    public CursosEmAndamentoAdapter(@NonNull Context context, List<Matricula> list) {
        super(context, 0, list);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.adapter_curso_em_andamento, parent, false);
        Matricula matricula = getItem(position);



        TextView text = root.findViewById(R.id.text_aula_adapter_curso_andamento);
        text.setText("* " + matricula.getNome());



        return root;
    }
}
