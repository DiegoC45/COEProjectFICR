package com.coe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.coe.R;
import com.coe.bean.Curso;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CursoAdapter extends ArrayAdapter<Curso> {
    private Context context;
    private ImageView mImageView;
    private TextView mTextView;

    public CursoAdapter(@NonNull Context context, List<Curso> cursos) {
        super(context, 0,cursos);
        this.context=context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.adapter_curso,null);
        Curso curso = getItem(position);
        mImageView=root.findViewById(R.id.image_curso);
        mTextView=root.findViewById(R.id.titulo_curso);
        Picasso.get().load(curso.getImage()).into(mImageView);
        mTextView.setText(curso.getNome());

        return root;

    }
}
