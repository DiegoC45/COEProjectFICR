package com.coe.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.coe.R;
import com.coe.bean.Curso;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CursoAdapter extends ArrayAdapter<Curso> implements Filterable {
    private Context context;
    private ImageView mImageView;
    private TextView mTextView;
    private List<Curso> cursoItens;
    private List<Curso> cursoFull;

    public CursoAdapter(@NonNull Context context, List<Curso> cursos) {
        super(context, 0,cursos);
        this.context=context;
        this.cursoItens = cursos;
        this.cursoFull = new ArrayList<>(cursoItens);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.adapter_curso,null);
        Curso curso = getItem(position);
        mImageView=root.findViewById(R.id.image_curso);
        mTextView=root.findViewById(R.id.titulo_curso);
        Picasso.get().load(curso.getImgUrl()).into(mImageView);
        mTextView.setText(curso.getNome());

        return root;

    }

    @NonNull
    @Override
    public Filter getFilter() {
        return exemploFilter;
    }

    public Filter exemploFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
          List<Curso> cursosSelecionado = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                cursosSelecionado.addAll(cursoFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Curso curso: cursoFull) {
                    if (curso.getNome().toLowerCase().contains(filterPattern)) {
                        cursosSelecionado.add(curso);
                    }

                }

            }
            FilterResults results = new FilterResults();
            results.values = cursosSelecionado;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
                cursoItens.clear();
                cursoItens.addAll((List) results.values);
                notifyDataSetChanged();
        }
    };
}
